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

import static com.stylelab.category.domain.top.QSweatshirtProductCategory.sweatshirtProductCategory;

@Repository
@RequiredArgsConstructor
public class SweatshirtProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                sweatshirtProductCategory.id,
                                sweatshirtProductCategory.productId,
                                sweatshirtProductCategory.storeId,
                                sweatshirtProductCategory.storeName,
                                sweatshirtProductCategory.productCategoryPath,
                                sweatshirtProductCategory.productCategoryName,
                                sweatshirtProductCategory.productMainImage,
                                sweatshirtProductCategory.productMainImageType,
                                sweatshirtProductCategory.name,
                                sweatshirtProductCategory.price,
                                sweatshirtProductCategory.discountPrice,
                                sweatshirtProductCategory.discountRate,
                                sweatshirtProductCategory.soldOut,
                                sweatshirtProductCategory.deleted,
                                sweatshirtProductCategory.createdAt,
                                sweatshirtProductCategory.updatedAt
                        )
                )
                .from(sweatshirtProductCategory)
                .where( 
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(sweatshirtProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return sweatshirtProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return sweatshirtProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = sweatshirtProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = sweatshirtProductCategory.price.goe(price1).and(sweatshirtProductCategory.price.loe(price2));
        } else {
            goePrice = sweatshirtProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
