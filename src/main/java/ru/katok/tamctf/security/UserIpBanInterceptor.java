package ru.katok.tamctf.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Transactional
@Getter
@Setter
@Slf4j
@EnableCaching
public class UserIpBanInterceptor implements HandlerInterceptor {
    @Override
    @Bean
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
/*        try {
                String userLastLoginIp = request.getRemoteAddr();
                if (cache.get(userLastLoginIp)) {
                    log.warn("Attempt to connect via banned ip: " + userLastLoginIp);
                    return false;
                } else {
                    return true;
                }
        } catch (Exception e) {*/
        return true;
    }
}
