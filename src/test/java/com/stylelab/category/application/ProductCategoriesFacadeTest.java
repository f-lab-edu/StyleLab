package com.stylelab.category.application;

import com.stylelab.category.exception.ProductCategoryError;
import com.stylelab.category.exception.ProductCategoryException;
import com.stylelab.category.service.ProductCategoriesService;
import com.stylelab.category.service.ProductCategoryTree;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductCategoriesFacadeTest {

    @Mock
    private ProductCategoryTree productCategoryTree;
    @Mock
    private ProductCategoriesService productCategoriesService;

    @InjectMocks
    private ProductCategoriesFacade productCategoriesFacade;

    @Test
    @DisplayName("상품 카테고리 목록 조회 실패 - 유효하지 않은 카테고리인 경우 ProductCategoryException(INVALID_PRODUCT_CATEGORY_PATH)이 발생한다.")
    public void failureFindAllProductCategoryConditions() throws Exception {
        //given
        Long productId = null;
        String productName = null;
        String productCategoryPath = "0001001";
        Integer price1 = null;
        Integer price2 = null;
        Integer discountRate = null;
        Pageable pageable = PageRequest.ofSize(10);

        //when
        ProductCategoryException productCategoryException = assertThrows(ProductCategoryException.class,
                () -> productCategoriesFacade.findAllProductCategoryConditions(
                        productId, productName, productCategoryPath, price1, price2, discountRate, pageable
                )
        );

        //then
        assertEquals(ProductCategoryError.INVALID_PRODUCT_CATEGORY_PATH, productCategoryException.getServiceError());
        verify(productCategoriesService, times(0))
                .findAllProductCategoryConditions(any(), anyLong(), anyString(), anyString(), anyInt(), anyInt(), anyInt(), any());
    }
}
