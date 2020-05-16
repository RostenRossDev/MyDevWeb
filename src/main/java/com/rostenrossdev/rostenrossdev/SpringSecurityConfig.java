package com.rostenrossdev.rostenrossdev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private com.rostenrossdev.rostenrossdev.authhandler.LoginSuccesHandler successHandler;
    @Autowired
    private com.rostenrossdev.rostenrossdev.models.service.JpaUserDetailsService userDetailsService;
    
    @Autowired
    public void configuredGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/css/**","/js/**","/images/**","/home").permitAll()
        .antMatchers("/uploads").hasAnyRole("USER")
        .anyRequest().authenticated()
        .and()
            .formLogin()
                .successHandler(successHandler)
                .loginPage("/login")
            .permitAll()
        .and()
        .logout().permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/error_403");
    }

    
    
}