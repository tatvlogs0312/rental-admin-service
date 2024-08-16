package org.example.administrationservice.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.administrationservice.models.key_cloak.KeyCloakTokenResDTO;
import org.example.administrationservice.proxy.KeyCloakProxy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCacheService {

    private final String PREDIX = "keycloak_superuser_token";

    private final RedisService redisService;

    private final KeyCloakProxy keyCloakProxy;

    public String getToken() {
        String tokenFromRedis = redisService.getValue(PREDIX);
        if (StringUtils.isNotBlank(tokenFromRedis)) {
            return tokenFromRedis;
        } else {
            KeyCloakTokenResDTO keyCloakTokenResDTO = keyCloakProxy.loginKeyCloak();
            String token = keyCloakTokenResDTO.getAccessToken();
            redisService.setValue(PREDIX, token, keyCloakTokenResDTO.getExpiresIn());

            return token;
        }
    }
}
