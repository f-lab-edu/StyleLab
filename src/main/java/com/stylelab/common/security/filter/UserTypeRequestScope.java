package com.stylelab.common.security.filter;

import com.stylelab.common.security.constant.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor
@AllArgsConstructor
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserTypeRequestScope {

    private UserType userType;

    public void  createUserType(String type) {
        this.userType = UserType.of(type);
    }
}
