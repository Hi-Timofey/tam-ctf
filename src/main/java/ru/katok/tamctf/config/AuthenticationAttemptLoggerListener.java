package ru.katok.tamctf.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import ru.katok.tamctf.service.UserService;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationAttemptLoggerListener implements ApplicationListener<AbstractAuthenticationEvent> {

    private final UserService userService;

    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        Authentication auth = event.getAuthentication();
        if (auth == null)
            return;
        WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
        if (details == null)
            return;
        String ipAddress = details.getRemoteAddress();
        String username = auth.getName();

        if (!(event instanceof AbstractAuthenticationFailureEvent)) {
            userService.updateLoggedUserIp(username, ipAddress);
            log.debug("Succesful logged authentication attemped from {} from: {}", username,ipAddress);
        }
    }
}

