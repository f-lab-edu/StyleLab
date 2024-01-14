package com.stylelab.common.security.service;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.common.security.constant.UserType;
import com.stylelab.common.security.filter.UserTypeRequestScope;
import com.stylelab.store.repository.StoreStaffRepository;
import com.stylelab.user.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.stylelab.common.exception.ServiceError.UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private LoadUserByUsernameStrategyMap loadUserByUsernameStrategyMap;
    @Mock
    private LoadUserByUsernameStrategy usersLoadUserByUsernameStrategy;
    @Mock
    private LoadUserByUsernameStrategy storeLoadUserByUsernameStrategy;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private StoreStaffRepository storeStaffRepository;
    @Mock
    private UserTypeRequestScope userTypeRequestScope;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void init() {
        loadUserByUsernameStrategyMap = new LoadUserByUsernameStrategyMap(
                Map.of(
                        UserType.USER, usersLoadUserByUsernameStrategy,
                        UserType.STORE, storeLoadUserByUsernameStrategy
                )
        );
        customUserDetailsService = new CustomUserDetailsService(loadUserByUsernameStrategyMap, userTypeRequestScope);
    }

    @Test
    @DisplayName("유효하지 않은 사용자는 usersLoadUserByUsernameStrategy 조회에 실패")
    public void failureLoadUserByUsername_01() {
        // given
        String username = "test@gmail.,..com";
        doThrow(new ServiceException(UNAUTHORIZED, UNAUTHORIZED.getMessage()))
                .when(usersLoadUserByUsernameStrategy).loadUserByUsername(anyString());

        // when
        assertThrows(ServiceException.class,
                () -> customUserDetailsService.loadUserByUsername(UserType.USER, username));

        // then
        verify(usersLoadUserByUsernameStrategy, times(1))
                .loadUserByUsername(anyString());
    }

    @Test
    @DisplayName("유효하지 않은 사용자는 storeLoadUserByUsernameStrategy 조회에 실패")
    public void failureLoadUserByUsername_02() {
        // given
        String username = "test@gmail.,..com";
        doThrow(new ServiceException(UNAUTHORIZED, UNAUTHORIZED.getMessage()))
                .when(storeLoadUserByUsernameStrategy).loadUserByUsername(anyString());

        // when
        assertThrows(ServiceException.class,
                () -> customUserDetailsService.loadUserByUsername(UserType.STORE, username));

        // then
        verify(storeLoadUserByUsernameStrategy, times(1))
                .loadUserByUsername(anyString());
    }
}
