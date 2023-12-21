package com.stylelab.user.service;

import com.stylelab.user.domain.Users;
import com.stylelab.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;


    @Override
    @Transactional
    public void signup(Users users) {
        usersRepository.save(users);
    }
}
