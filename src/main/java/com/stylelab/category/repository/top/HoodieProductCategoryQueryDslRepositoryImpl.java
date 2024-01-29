package com.stylelab.category.repository.top;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stylelab.category.repository.ProductCategoryQueryDslRepository;
import com.stylelab.category.repository.dto.ProductCategoryCollection;
import com.stylelab.category.repository.dto.QProductCategoryCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.stylelab.category.domain.top.QHoodieProductCategory.hoodieProductCategory;

@Repository
@RequiredArgsConstructor
public class HoodieProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                hoodieProductCategory.id,
                                hoodieProductCategory.productId,
                                hoodieProductCategory.storeId,
                                hoodieProductCategory.storeName,
                                hoodieProductCategory.productCategoryPath,
                                hoodieProductCategory.productCategoryName,
                                hoodieProductCategory.productMainImage,
                                hoodieProductCategory.productMainImageType,
                                hoodieProductCategory.name,
                                hoodieProductCategory.price,
                                hoodieProductCategory.discountPrice,
                                hoodieProductCategory.discountRate,
                                hoodieProductCategory.soldOut,
                                hoodieProductCategory.deleted,
                                hoodieProductCategory.createdAt,
                                hoodieProductCategory.updatedAt
                        )
                )
                .from(hoodieProductCategory)
                .where( 
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(hoodieProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return hoodieProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return hoodieProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = hoodieProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = hoodieProductCategory.price.goe(price1).and(hoodieProductCategory.price.loe(price2));
        } else {
            goePrice = hoodieProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
