package com.stylelab.common.security.service;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.user.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.stylelab.common.exception.ServiceError.UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("유효하지 않은 사용자는 loadUserByUsername 조회에 실패")
    public void failureLoadUserByUsername() {
        // given
        String username = "test@gmail.,..com";
        doThrow(new ServiceException(UNAUTHORIZED, UNAUTHORIZED.getMessage()))
                .when(usersRepository).findByEmail(anyString());

        // when
        assertThrows(ServiceException.class,
                () -> customUserDetailsService.loadUserByUsername(username));

        // then
        verify(usersRepository, times(1))
                .findByEmail(anyString());
    }
}
