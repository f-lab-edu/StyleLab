package com.stylelab.common.security.service;

import com.stylelab.common.security.constant.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final LoadUserByUsernameStrategyMap loadUserByUsernameStrategyMap;

    public UserDetails loadUserByUsername(UserType userType, String username) throws UsernameNotFoundException {
        LoadUserByUsernameStrategy loadUserByUsernameStrategy = loadUserByUsernameStrategyMap.getLoadUserByUsernameStrategyMap().get(userType);
        return loadUserByUsernameStrategy.loadUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
