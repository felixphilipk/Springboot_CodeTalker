package com.example.codetalker.config;


import org.springframework.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;




@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .anyRequest().authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(jwtAuthenticationConverter());
        }

 @Bean
 public JwtAuthenticationConverter jwtAuthenticationConverter(){
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthorityPrefix("SCOPE_");
    converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return converter;

 }    
}   
        
    



    

