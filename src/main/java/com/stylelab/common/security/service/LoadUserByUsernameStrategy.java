package com.stylelab.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface LoadUserByUsernameStrategy {

    UserDetails loadUserByUsername(String username);
}
