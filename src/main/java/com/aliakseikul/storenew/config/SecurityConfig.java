package com.aliakseikul.storenew.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(
                                        new AntPathRequestMatcher("/images/**"),
                                        new AntPathRequestMatcher("/api/enum/**"),


                                        new AntPathRequestMatcher("/api/"),
                                        new AntPathRequestMatcher("/api/product/**"),
                                        new AntPathRequestMatcher("/api/login"),
                                        new AntPathRequestMatcher("/api/register"),
                                        new AntPathRequestMatcher("/api/registration"),
                                        new AntPathRequestMatcher("/api/authentication/login"),
                                        new AntPathRequestMatcher("/swagger-ui/index.html")
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .formLogin((formLogin) ->
//                formLogin
//                        .usernameParameter("request.userNickname")
//                        .passwordParameter("request.userPassword")
//                        .loginPage("/login")
//                        .failureUrl("/login?failed")
//                        .loginProcessingUrl("/authentication/login/"))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
