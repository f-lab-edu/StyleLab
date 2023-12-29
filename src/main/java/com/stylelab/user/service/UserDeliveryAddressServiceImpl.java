package com.stylelab.user.service;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.user.domain.UserDeliveryAddress;
import com.stylelab.user.exception.UsersException;
import com.stylelab.user.repository.UserDeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.stylelab.common.exception.ServiceError.BAD_REQUEST;
import static com.stylelab.user.exception.UsersError.DELIVERY_ADDRESS_SAVE_FAIL;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDeliveryAddressServiceImpl implements UserDeliveryAddressService{

    private final UserDeliveryAddressRepository userDeliveryAddressRepository;

    @Override
    @Transactional
    public void createUserDeliveryAddress(UserDeliveryAddress userDeliveryAddress) {
        try {
            userDeliveryAddressRepository.save(userDeliveryAddress);
        } catch (DataAccessException dataException) {
            log.error("data access exception", dataException);
            throw new UsersException(DELIVERY_ADDRESS_SAVE_FAIL, DELIVERY_ADDRESS_SAVE_FAIL.getMessage(), dataException);
        } catch (RuntimeException runtimeException) {
            log.error("runtime exception", runtimeException);
            throw new ServiceException(BAD_REQUEST, BAD_REQUEST.getMessage(), runtimeException);
        }
    }
}
