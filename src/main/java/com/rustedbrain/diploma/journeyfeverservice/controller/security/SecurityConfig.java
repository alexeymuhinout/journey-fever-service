package com.rustedbrain.diploma.journeyfeverservice.controller.security;

import com.rustedbrain.diploma.journeyfeverservice.model.persistence.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService customUserDetailsService;

    private final SecurityConfigurer securityConfigurerAdapter;

    @Autowired
    public SecurityConfig(@Qualifier("customUserDetailsService") UserDetailsService customUserDetailsService, @Qualifier("authTokenConfig") SecurityConfigurer securityConfigurerAdapter) {
        this.customUserDetailsService = customUserDetailsService;
        this.securityConfigurerAdapter = securityConfigurerAdapter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] methodUnsecured = {"/login/authenticate", "/login/register"};
        String[] methodSecured = {"/travel/*", "/swagger-ui.html"};
        String[] adminAndModerSecured = {"/login/deregister"};

        http.csrf().disable()
                .authorizeRequests().antMatchers(methodUnsecured).permitAll()
                .antMatchers(methodSecured).authenticated()
                .antMatchers(adminAndModerSecured).hasAnyAuthority(Role.ADMINISTRATOR.name(), Role.MODERATOR.name())
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/swagger-ui.html").failureUrl("/login?error=true").permitAll()
                .and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout").logoutSuccessUrl("/login");

        http.apply(securityConfigurerAdapter);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}