package com.api.business_manager_api.Config;

import com.api.business_manager_api.Security.FilterToken;
import com.api.business_manager_api.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public FilterToken filterTokenJwt() {
        return new FilterToken();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                    .csrf().disable()
                    .authorizeHttpRequests(
                            authorizeConfig -> {
                                authorizeConfig.requestMatchers(HttpMethod.POST, "/user").permitAll();
                                authorizeConfig.requestMatchers(HttpMethod.POST, "/auth**").permitAll();
                                authorizeConfig.requestMatchers("/error").permitAll();
                                authorizeConfig.requestMatchers("/user/**").authenticated();
                                authorizeConfig.requestMatchers("/product").authenticated();
                                authorizeConfig.requestMatchers("/product/**").authenticated();
                                authorizeConfig.requestMatchers("/customer").authenticated();
                                authorizeConfig.requestMatchers("/customer/**").authenticated();
                                authorizeConfig.requestMatchers("/category").authenticated();
                                authorizeConfig.requestMatchers("/category/**").authenticated();
                                authorizeConfig.requestMatchers("/order/**").authenticated();
                                authorizeConfig.requestMatchers(HttpMethod.POST, "/order").authenticated();
                                authorizeConfig.anyRequest().authenticated();
                            })
                    .userDetailsService(userDetailsService)
                    .httpBasic()
                    .and()
                    .httpBasic();
            http.addFilterBefore(filterTokenJwt(), UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }
}
