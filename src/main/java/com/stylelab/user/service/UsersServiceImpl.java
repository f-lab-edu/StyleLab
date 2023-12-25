package com.stylelab.user.service;

import com.stylelab.common.exception.ServiceError;
import com.stylelab.common.exception.ServiceException;
import com.stylelab.user.domain.Users;
import com.stylelab.user.exception.UsersException;
import com.stylelab.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.stylelab.common.exception.ServiceError.*;
import static com.stylelab.user.exception.UsersError.USERS_SAVE_FAIL;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public void signup(final Users users) {
        try {
            usersRepository.save(users);
        } catch (DataAccessException dataException) {
            log.error("data access exception", dataException);
            throw new UsersException(USERS_SAVE_FAIL, USERS_SAVE_FAIL.getMessage(), dataException);
        } catch (RuntimeException runtimeException) {
            log.error("runtime exception", runtimeException);
            throw new ServiceException(BAD_REQUEST, BAD_REQUEST.getMessage(), runtimeException);
        }
    }
}
