package com.rustedbrain.diploma.journeyfeverservice.controller.security;

import com.rustedbrain.diploma.journeyfeverservice.controller.security.util.TokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthTokenFilter extends GenericFilterBean {

    private UserDetailsService customUserDetailsService;
    public static final String AUTH_TOKEN_HEADER_NAME = "X-AUTH-TOKEN";

    public AuthTokenFilter(UserDetailsService userDetailsService) {
        this.customUserDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String authToken = httpServletRequest.getHeader(AUTH_TOKEN_HEADER_NAME);

            if (StringUtils.hasText(authToken)) {
                String username = TokenUtil.getUserNameFromToken(authToken);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (TokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
                            userDetails.getPassword(), userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
