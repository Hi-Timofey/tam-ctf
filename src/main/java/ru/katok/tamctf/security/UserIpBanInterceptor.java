package ru.katok.tamctf.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.katok.tamctf.service.RedisService;


@Slf4j
@RequiredArgsConstructor
public class UserIpBanInterceptor implements HandlerInterceptor {

    private final RedisService redisService;

    @Override
    @Bean
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userLastLoginIp = request.getRemoteAddr();
        log.info("HERE HERE IP ACCESS :" + userLastLoginIp);
        if (redisService.isBanned(userLastLoginIp)) {
            log.warn("Attempt to connect via banned ip: " + userLastLoginIp);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        } else {
            return true;
        }
    }
}

