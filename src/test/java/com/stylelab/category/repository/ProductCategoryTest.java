package com.stylelab.category.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stylelab.category.repository.dto.ProductCategoryCollection;
import com.stylelab.category.repository.dto.QProductCategoryCollection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.stylelab.category.domain.QTopProductCategory.topProductCategory;


@Slf4j
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class ProductCategoryTest {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Test
    @DisplayName("상의 카테고리 목록 조회")
    public void findAllTopProductCategoryConditionsTest() {
        Long productId = 80L;
        Pageable pageable = PageRequest.ofSize(10);
        Slice<ProductCategoryCollection> productCategoryCollections = findAllTopProductCategoryConditions(productId, pageable);

        log.info("isLast: {}", productCategoryCollections.isLast());
        for (ProductCategoryCollection productCategoryCollection : productCategoryCollections.getContent()) {
            log.info("product: {}", productCategoryCollection);
        }
    }

    public Slice<ProductCategoryCollection> findAllTopProductCategoryConditions(Long productId, Pageable pageable) {

        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                topProductCategory.id,
                                topProductCategory.productId,
                                topProductCategory.storeId,
                                topProductCategory.storeName,
                                topProductCategory.productCategoryPath,
                                topProductCategory.productCategoryName,
                                topProductCategory.productMainImage,
                                topProductCategory.productMainImageType,
                                topProductCategory.name,
                                topProductCategory.price,
                                topProductCategory.discountPrice,
                                topProductCategory.discountRate,
                                topProductCategory.soldOut,
                                topProductCategory.deleted,
                                topProductCategory.createdAt,
                                topProductCategory.updatedAt
                        )
                )
                .from(topProductCategory)
                .orderBy(topProductCategory.productId.desc())
                .where(ltProductId(productId))
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private boolean isLast(Pageable pageable, List<ProductCategoryCollection> results) {
        boolean isLast = false;
        if (results.size() > pageable.getPageSize()) {
            isLast = true;
            results.remove(pageable.getPageSize());
        }

        return isLast;
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return topProductCategory.productId.lt(productId);
    }
}
