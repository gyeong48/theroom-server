package com.theroom.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final AccessDeniedHandler deniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Value("${allow.origin.path}")
    private String allowedOrigin;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/content/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/portfolio").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/portfolio/read/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/portfolio/view/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/contact/add").permitAll()
                        .requestMatchers("/api/account/login").permitAll()
                        .requestMatchers("/api/account/logoutSuccess").permitAll()
                        .anyRequest().hasRole("ADMIN")
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/account/login")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/api/account/logout")
                        .logoutSuccessUrl("/api/account/logoutSuccess")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(deniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(allowedOrigin);
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
