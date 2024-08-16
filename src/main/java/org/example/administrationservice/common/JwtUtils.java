package org.example.administrationservice.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Slf4j
public class JwtUtils {

    public static String getUsername() {
        try {
            HttpServletRequest request;
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            if (Objects.nonNull(servletRequestAttributes)) {
                request = servletRequestAttributes.getRequest();
                String authorization = request.getHeader("Authorization");
                String token = StringUtils.isNotBlank(authorization) ? authorization.replace("Bearer ", "") : "";
                DecodedJWT dJWT = JWT.decode(token);
                return dJWT.getClaim("sub").asString();
            } else {
                return "";
            }
        } catch (Exception e) {
            log.info("JwtUtils getUsername - Exception: {}", e.getMessage());
        }
        return "";
    }

    public static String getToken() {
        try {
            HttpServletRequest request;
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            if (Objects.nonNull(servletRequestAttributes)) {
                request = servletRequestAttributes.getRequest();
                String authorization = request.getHeader("Authorization");
                return StringUtils.isNotBlank(authorization) ? authorization.replace("Bearer ", "") : "";
            } else {
                return "";
            }
        } catch (Exception e) {
            log.info("JwtUtils getToken - Exception: {}", e.getMessage());
        }
        return "";
    }
}
