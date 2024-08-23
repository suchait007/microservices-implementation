package com.retail.api.gateway.config;


import com.retail.api.gateway.security.AuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity, JwtDecoder jwtDecoder
            , AuthenticationFailureHandler authenticationFailureHandler) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/actuator/**")
                .permitAll()
                .anyRequest()
                .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder))
                        .authenticationEntryPoint(authenticationFailureHandler));

        return httpSecurity.build();

    }

    @Bean
    public JwtDecoder jwtDecoder(@Value("${security.jwt}") String jwt) {
        return NimbusJwtDecoder.withJwkSetUri(jwt).build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler();
    }
}
