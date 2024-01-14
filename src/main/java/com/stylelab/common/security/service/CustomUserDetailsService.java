package com.stylelab.common.security.service;

import com.stylelab.common.security.constant.UserType;
import com.stylelab.common.security.filter.UserTypeRequestScope;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final LoadUserByUsernameStrategyMap loadUserByUsernameStrategyMap;
    private final UserTypeRequestScope userTypeRequestScope;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoadUserByUsernameStrategy loadUserByUsernameStrategy =
                loadUserByUsernameStrategyMap.getLoadUserByUsernameStrategyMap().get(userTypeRequestScope.getUserType());
        return loadUserByUsernameStrategy.loadUserByUsername(username);
    }

    public UserDetails loadUserByUsername(UserType userType, String username) throws UsernameNotFoundException {
        LoadUserByUsernameStrategy loadUserByUsernameStrategy =
                loadUserByUsernameStrategyMap.getLoadUserByUsernameStrategyMap().get(userType);
        return loadUserByUsernameStrategy.loadUserByUsername(username);
    }

}
