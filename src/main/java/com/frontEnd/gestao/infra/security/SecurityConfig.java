package com.frontEnd.gestao.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {


@Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
    http.authorizeRequests(auth->{
        auth.requestMatchers("/candidato/login").permitAll()
                .requestMatchers("/").permitAll()

                .requestMatchers("/candidato/cadastro").permitAll()
                .requestMatchers("/candidato/signIn").permitAll()
                .requestMatchers("/empresa/cadastro").permitAll()
                .requestMatchers("/empresa/login").permitAll()
                .requestMatchers("/empresa/signIn").permitAll()

        ;


        auth.anyRequest().authenticated();
    })


//    ero retorna na tela de login
            .formLogin(form -> form.loginPage("/candidato/login"));

return http.build();

}

}
