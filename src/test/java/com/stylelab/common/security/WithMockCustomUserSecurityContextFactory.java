package com.stylelab.common.security;

import com.stylelab.common.annotation.WithAccount;
import com.stylelab.common.security.constant.UserType;
import com.stylelab.common.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public SecurityContext createSecurityContext(WithAccount annotation) {
        String email = annotation.email();
        String role = annotation.role();
        UserType userType = annotation.type();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userType, email);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, List.of(new SimpleGrantedAuthority(role)));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return securityContext;
    }
}
