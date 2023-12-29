package com.stylelab.user.service;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.user.domain.UserDeliveryAddress;
import com.stylelab.user.repository.UserDeliveryAddressRepository;
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
public class UserDeliveryAddressTest {

    @Mock
    private UserDeliveryAddressRepository userDeliveryAddressRepository;

    @InjectMocks
    private UserDeliveryAddressServiceImpl userDeliveryAddressService;

    @Nested
    @DisplayName("배송지 등록 테스트")
    public class CreateUserDeliveryAddressTest {

        @Test
        @DisplayName("배송지 등록 실패 - DataAccessException")
        public void failureCreateUserDeliveryAddress_01() {
            // given
            UserDeliveryAddress userDeliveryAddress = UserDeliveryAddress.builder()
                    .build();

            doThrow(new DataIntegrityViolationException("insert fail"))
                    .when(userDeliveryAddressRepository).save(any());

            // when
            assertThrows(ServiceException.class,
                    () -> userDeliveryAddressService.createUserDeliveryAddress(userDeliveryAddress));

            // then
            verify(userDeliveryAddressRepository, times(1))
                    .save(any());
        }

        @Test
        @DisplayName("배송지 등록 실패 - RuntimeException")
        public void failureCreateUserDeliveryAddress_02() {
            // given
            UserDeliveryAddress userDeliveryAddress = UserDeliveryAddress.builder()
                    .build();

            doThrow(new RuntimeException("insert fail"))
                    .when(userDeliveryAddressRepository).save(any());

            // when
            assertThrows(ServiceException.class,
                    () -> userDeliveryAddressService.createUserDeliveryAddress(userDeliveryAddress));

            // then
            verify(userDeliveryAddressRepository, times(1))
                    .save(any());
        }
    }
}
