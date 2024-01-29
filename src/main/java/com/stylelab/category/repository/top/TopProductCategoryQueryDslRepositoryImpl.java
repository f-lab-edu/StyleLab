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

import static com.stylelab.category.domain.top.QTopProductCategory.topProductCategory;

@Repository
@RequiredArgsConstructor
public class TopProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
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
                .where(
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(topProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return topProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return topProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = topProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = topProductCategory.price.goe(price1).and(topProductCategory.price.loe(price2));
        } else {
            goePrice = topProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
