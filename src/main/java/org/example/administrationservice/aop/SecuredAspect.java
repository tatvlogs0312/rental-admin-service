package org.example.administrationservice.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.administrationservice.common.JwtUtils;
import org.example.administrationservice.exception.AuthorizationException;
import org.example.administrationservice.exception.ExceptionEnums;
import org.example.administrationservice.exception.ForbiddenException;
import org.example.administrationservice.redis.RedisService;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SecuredAspect {

    private final RedisService redisService;

    @Before(value = "@annotation(org.example.administrationservice.aop.Secured)")
    public void before(JoinPoint j) {
        String username = JwtUtils.getUsername();
        if (StringUtils.isBlank(username)) {
            throw new AuthorizationException(ExceptionEnums.EX_NOT_AUTHORIZATION);
        }

        //Get role for use api
        MethodSignature signature = (MethodSignature) j.getSignature();
        Method method = signature.getMethod();
        var s = method.getAnnotation(Secured.class);
        String roleOfApi = s.role();

        //Verify role
        if (StringUtils.isNotBlank(roleOfApi)) {
            String roleOfUser = redisService.getValue(username + "_role");
            if (!Objects.equals(roleOfApi, roleOfUser)) {
                throw new ForbiddenException(ExceptionEnums.EX_FORBIDDEN);
            }
        }
    }
}
