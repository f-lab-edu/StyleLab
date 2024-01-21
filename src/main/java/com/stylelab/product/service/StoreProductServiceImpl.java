package com.stylelab.product.service;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.product.domain.Product;
import com.stylelab.product.exception.ProductError;
import com.stylelab.product.exception.ProductException;
import com.stylelab.product.repository.ProductRepository;
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
public class StoreProductServiceImpl implements StoreProductService {

    private final ProductRepository productRepository;

    @Override
    public Long createStoreProduct(final Product product) {
        try {
            productRepository.save(product);
            return product.getProductId();
        } catch (DataAccessException exception) {
            throw new ProductException(ProductError.PRODUCT_SAVE_FAIL, exception);
        } catch (RuntimeException exception) {
            throw new ServiceException(BAD_REQUEST, exception);
        }
    }
}
