package com.stylelab.user.service;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.user.domain.Users;
import com.stylelab.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Nested
    @DisplayName("회원 가입 테스트")
    public class SignupTest {

        @Test
        @DisplayName("회원 가입 실패 - DataAccessException")
        public void failureSignup_01() {
            // given
            Users users = Users.builder()
                    .email("test@gmail.com")
                    .name("한규빈")
                    .nickname("coby")
                    .password("test123412!")
                    .phoneNumber("01011111111")
                    .build();
            doThrow(new DataIntegrityViolationException("insert fail"))
                    .when(usersRepository).save(any());

            // when
            assertThrows(ServiceException.class,
                    () -> usersService.signup(users));

            // then
            verify(usersRepository, times(1))
                    .save(any());
        }

        @Test
        @DisplayName("회원 가입 실패 - RuntimeException")
        public void failureSignup_02() {
            // given
            Users users = Users.builder()
                    .email("test@gmail.com")
                    .name("한규빈")
                    .nickname("coby")
                    .password("test123412!")
                    .phoneNumber("01011111111")
                    .build();
            doThrow(new RuntimeException("insert fail"))
                    .when(usersRepository).save(any());

            // when
            assertThrows(ServiceException.class,
                    () -> usersService.signup(users));

            // then
            verify(usersRepository, times(1))
                    .save(any());
        }
    }
}
