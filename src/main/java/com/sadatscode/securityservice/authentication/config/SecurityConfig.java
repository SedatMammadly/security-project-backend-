package com.sadatscode.securityservice.authentication.config;


import com.sadatscode.securityservice.authentication.exception.CustomAccessDeniedHandler;
import com.sadatscode.securityservice.authentication.exception.CustomAuthenticationEntryPoint;
import com.sadatscode.securityservice.authentication.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter filter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
//               .csrf((csrf)->csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                  .csrf(AbstractHttpConfigurer::disable)
                 .exceptionHandling(exception ->
                         exception.accessDeniedHandler(accessDeniedHandler)
                                 .authenticationEntryPoint(authenticationEntryPoint)
                 )
                   .authorizeHttpRequests( auth ->
                    auth
                            .requestMatchers("/api/v1/auth/**").permitAll()
//                            .requestMatchers("/api/v1/user/**").permitAll()
                            .anyRequest().authenticated()
                )

                 .cors(Customizer.withDefaults())
                 .authenticationProvider(authenticationProvider)
                 .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

         return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8081"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
