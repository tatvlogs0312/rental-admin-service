package org.example.administrationservice.service.authentication.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.administrationservice.common.RsaUtils;
import org.example.administrationservice.common.Utils;
import org.example.administrationservice.constants.Constants;
import org.example.administrationservice.constants.MailTemplate;
import org.example.administrationservice.entity.User;
import org.example.administrationservice.enums.RoleEnum;
import org.example.administrationservice.exception.ApplicationException;
import org.example.administrationservice.models.auth.login.LoginReqDTO;
import org.example.administrationservice.models.auth.login.LoginResDTO;
import org.example.administrationservice.models.auth.register.NewUserReqDTO;
import org.example.administrationservice.models.key_cloak.KeyCloakCredentialsDTO;
import org.example.administrationservice.models.key_cloak.KeyCloakNewUserReqDTO;
import org.example.administrationservice.models.key_cloak.KeyCloakTokenReqDTO;
import org.example.administrationservice.models.key_cloak.KeyCloakTokenResDTO;
import org.example.administrationservice.models.mail.MailDTO;
import org.example.administrationservice.proxy.KeyCloakProxy;
import org.example.administrationservice.redis.TokenCacheService;
import org.example.administrationservice.repository.UserRepository;
import org.example.administrationservice.service.authentication.AuthenticationService;
import org.example.administrationservice.service.mail.MailService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final KeyCloakProxy keyCloakProxy;

    private final TokenCacheService tokenCacheService;

    private final RsaUtils rsaUtils;

    private final MailService mailService;

    @Override
    public LoginResDTO login(LoginReqDTO req) {
        KeyCloakTokenReqDTO keyCloakTokenReqDTO = KeyCloakTokenReqDTO.builder()
                .username(req.getUsername()).password(req.getPassword()).build();
        KeyCloakTokenResDTO keyCloakTokenResDTO = null;
        try {
            keyCloakTokenResDTO = keyCloakProxy.loginKeyCloak(keyCloakTokenReqDTO);
        } catch (Exception e) {
            //handle login error
            handleLoginError(req);
        }

        assert keyCloakTokenResDTO != null;

        LoginResDTO res = new LoginResDTO();
        res.setToken(keyCloakTokenResDTO.getAccessToken());
        Optional<User> userOptional = userRepository.findFirstByUsername(req.getUsername());
        userOptional.ifPresent(user -> res.setRole(user.getRole()));

        return res;
    }

    private void handleLoginError(LoginReqDTO req) {
        String token = tokenCacheService.getToken();
        var users = keyCloakProxy.searchByUsername(req.getUsername(), token);
        if (CollectionUtils.isEmpty(users)) {
            throw new ApplicationException("Tài khoản không tồn tại");
        } else {
            throw new ApplicationException("Mật khẩu không chính xác");
        }
    }

    @Override
    public void createNewUser(NewUserReqDTO req) {
        Optional<User> userOptional = userRepository.findFirstByUsername(req.getUsername());
        if (userOptional.isPresent()) {
            throw new ApplicationException("Tài khoản đã tồn tại");
        }

        String password = Utils.generatePassword();

        //Tạo user trên keycloak
        KeyCloakNewUserReqDTO keyCloakNewUserReqDTO = KeyCloakNewUserReqDTO.builder()
                .enabled(true)
                .username(req.getUsername())
                .credentials(List.of(KeyCloakCredentialsDTO.builder()
                                .temporary(false)
                                .type(Constants.PASSWORD)
                                .value(password)
                        .build()))
                .build();
        keyCloakProxy.createUser(keyCloakNewUserReqDTO, tokenCacheService.getToken());

        //Gửi password cho user qua email
        MailDTO mailDTO = MailDTO.builder()
                .mailTo(List.of(req.getEmail()))
                .mailCc(new ArrayList<>())
                .subject("[RENTAL] Mật khẩu người dùng " + req.getUsername())
                .content(String.format(MailTemplate.passwordTemplate, password))
                .build();
        mailService.sendMailHtml(mailDTO);

        //Lưu thông tin account
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setStatus("ACTIVE");
        user.setRole(RoleEnum.USER.name());
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        user.setPassword(rsaUtils.encryptData(password));
        userRepository.save(user);
    }
}
