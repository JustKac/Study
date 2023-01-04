package br.com.segsat.restwhitspringbootandjava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.segsat.restwhitspringbootandjava.security.jwt.JwtConfigurer;
import br.com.segsat.restwhitspringbootandjava.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authorize -> {
                    try {
                        authorize
                            .requestMatchers(
                                "/auth/signin",
                                 "/auth/refrech", 
                                 "/v3/api-docs/**",
                                 "/swagger-ui/**").permitAll()
                            .requestMatchers("/api/**").authenticated()
                            .requestMatchers("/users").denyAll()
                            .and()
                                .cors()
                            .and()
                                .apply(new JwtConfigurer(tokenProvider));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });                      
        // .requestMatchers("/db/**").access(new
        // WebExpressionAuthorizationManager("hasRole('ADMIN') and hasRole('DBA')"))
        // .requestMatchers("/db/**").access(AuthorizationManagers.allOf(AuthorityAuthorizationManager.hasRole("ADMIN"),
        // AuthorityAuthorizationManager.hasRole("DBA")))
        // .anyRequest().denyAll()
        // .authorizeHttpRequests((authz) -> authz
        // .anyRequest().authenticated()
        // );
        return http.build();
    }

}