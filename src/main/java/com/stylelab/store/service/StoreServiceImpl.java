package com.stylelab.store.service;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.store.domain.Store;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.exception.StoreException;
import com.stylelab.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.stylelab.common.exception.ServiceError.BAD_REQUEST;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public void applyStore(Store store) {
        try {
            storeRepository.save(store);
        } catch (DataAccessException exception) {
            throw new StoreException(StoreError.STORE_AND_STORE_STAFF_SAVE_FAIL, StoreError.STORE_AND_STORE_STAFF_SAVE_FAIL.getMessage(), exception);
        } catch (RuntimeException exception) {
            throw new ServiceException(BAD_REQUEST, BAD_REQUEST.getMessage(), exception);
        }
    }
}
