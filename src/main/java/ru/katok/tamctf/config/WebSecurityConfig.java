package ru.katok.tamctf.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf( (csrf) -> csrf
                        .csrfTokenRepository(new CookieCsrfTokenRepository())
                        .disable()
                ).authorizeHttpRequests(requests -> requests
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/admin/**").hasRole("MODERATOR")
                        .requestMatchers("/", "/index", "/signup", "/api/v1/*").hasRole("USER")
                        .requestMatchers("/favicon.ico").permitAll()
                        .anyRequest().authenticated()
                ).formLogin((form) -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/api/v1/me")
                ).logout((logout) -> logout
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        /**
         *  Определяем промежуточные роли между админом и юзером
         */
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_MODERATOR \n ROLE_MODERATOR > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    //FUTURE: and, if using method security also add
//    @Bean
//    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
//        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
//        expressionHandler.setRoleHierarchy(roleHierarchy);
//        return expressionHandler;
//    }


    @Bean
    public AuthenticationManager daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authenticationProvider);
    }
}