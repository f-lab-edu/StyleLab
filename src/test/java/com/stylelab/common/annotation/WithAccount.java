package com.stylelab.common.annotation;

import com.stylelab.common.security.WithMockCustomUserSecurityContextFactory;
import com.stylelab.common.security.constant.UserType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithAccount {
    String email();
    String role();
    UserType type();
}
