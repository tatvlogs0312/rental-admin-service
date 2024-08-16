package org.example.administrationservice.proxy;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.constants.Constants;
import org.example.administrationservice.exception.ApplicationException;
import org.example.administrationservice.models.key_cloak.KeyCloakNewUserReqDTO;
import org.example.administrationservice.models.key_cloak.KeyCloakTokenReqDTO;
import org.example.administrationservice.models.key_cloak.KeyCloakTokenResDTO;
import org.example.administrationservice.models.key_cloak.KeyCloakUserResDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeyCloakProxy extends BaseProxy {

    @Value("${key-cloak.url}")
    private String keyCloakUrl;

    @Value("${key-cloak.client-id}")
    private String clientId;

    @Value("${key-cloak.realms}")
    private String realms;

    @Value("${key-cloak.username}")
    private String username;

    @Value("${key-cloak.password}")
    private String password;

    public KeyCloakTokenResDTO loginKeyCloak(KeyCloakTokenReqDTO dto) {
        try {

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", dto.getUsername());
            map.add("password", dto.getPassword());
            map.add("client_id", clientId);
            map.add("grant_type", Constants.PASSWORD);

            String url = keyCloakUrl + "/realms/" + realms + "/protocol/openid-connect/token";

            return this.post(url, this.initHeaderFormUrlencoded(), map, KeyCloakTokenResDTO.class);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public KeyCloakTokenResDTO loginKeyCloak() {
        try {

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", username);
            map.add("password", password);
            map.add("client_id", clientId);
            map.add("grant_type", Constants.PASSWORD);

            String url = keyCloakUrl + "/realms/" + realms + "/protocol/openid-connect/token";

            return this.post(url, this.initHeaderFormUrlencoded(), map, KeyCloakTokenResDTO.class);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<KeyCloakUserResDTO> searchByUsername(String username, String token) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(keyCloakUrl + "/admin/realms/" + realms + "/users")
                    .queryParam("username", username)
                    .toUriString();

            var response = this.get(url, initHeader(token), KeyCloakUserResDTO[].class);
            return Arrays.stream(response).toList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public void createUser(KeyCloakNewUserReqDTO keyCloakNewUserReqDTO, String token) {
        try{
            String url = keyCloakUrl + "/admin/realms/" + realms + "/users";
            this.post(url, initHeader(token), keyCloakNewUserReqDTO, Object.class);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
