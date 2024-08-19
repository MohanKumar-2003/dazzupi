package com.payment.sampleupi.security;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtTokenProvider tokenProvider;

    @Autowired
    private final CustomUserDetailsService customUserDetailsService;
    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider,CustomUserDetailsService customUserDetailsService){
        this.tokenProvider=jwtTokenProvider;
        this.customUserDetailsService=customUserDetailsService;
    }




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt=getJwtRequest(request);
            if(jwt!=null && tokenProvider.validateToken(jwt))
            {
                Long user_id=tokenProvider.getUserIdFromToken(jwt);
                UserPrincipal userPrincipal=(UserPrincipal) customUserDetailsService.loadUserById(user_id);
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(
                        userPrincipal,null,userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e){
            logger.error("Could not set user auth");
        }
        filterChain.doFilter(request,response);
    }

    private String getJwtRequest(HttpServletRequest request) {
    String bearerToken=request.getHeader("Authorization");
    if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
     return bearerToken.substring(7);
    }
    return null;
    }
}
