package com.stylelab.common.security;

import com.stylelab.user.constant.UsersRole;
import com.stylelab.user.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private Users users;
    private final String email;
    private final String password;
    private final UsersRole usersRole;
    private final Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserPrincipal create(Users users) {
        return new UserPrincipal(
                Users.createUser(users.getUserId()),
                users.getEmail(),
                users.getPassword(),
                users.getRole(),
                Collections.singletonList(new SimpleGrantedAuthority(UsersRole.ROLE_USER.name()))
        );
    }
}
