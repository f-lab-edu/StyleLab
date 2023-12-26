package com.stylelab.user.presentation.application;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.user.application.UsersFacade;
import com.stylelab.user.presentation.request.SignupRequest;
import com.stylelab.user.service.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UsersFacadeTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersFacade usersFacade;

    @Nested
    @DisplayName("회원 가입 테스트")
    public class SignupTest {

        @Test
        @DisplayName("회원 가입 실패 - 비밀번호와 비밀번호 확인 값이 다른 경우")
        public void failureSignup_01() {
            // given
            SignupRequest signupRequest = SignupRequest.builder()
                    .email("coby@gmail..com")
                    .password("test1234123!@")
                    .confirmPassword("test1234123!@!")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();

            // when
            assertThrows(ServiceException.class,
                    () -> usersFacade.signup(signupRequest));

            // then
            verify(usersService, times(0))
                    .signup(any());
        }
    }
}
