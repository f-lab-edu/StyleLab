package com.stylelab.category.repository.outer;

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

import static com.stylelab.category.domain.outer.QBubbleJacketProductCategory.bubbleJacketProductCategory;

@Repository
@RequiredArgsConstructor
public class BubbleJacketProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                bubbleJacketProductCategory.id,
                                bubbleJacketProductCategory.productId,
                                bubbleJacketProductCategory.storeId,
                                bubbleJacketProductCategory.storeName,
                                bubbleJacketProductCategory.productCategoryPath,
                                bubbleJacketProductCategory.productCategoryName,
                                bubbleJacketProductCategory.productMainImage,
                                bubbleJacketProductCategory.productMainImageType,
                                bubbleJacketProductCategory.name,
                                bubbleJacketProductCategory.price,
                                bubbleJacketProductCategory.discountPrice,
                                bubbleJacketProductCategory.discountRate,
                                bubbleJacketProductCategory.soldOut,
                                bubbleJacketProductCategory.deleted,
                                bubbleJacketProductCategory.createdAt,
                                bubbleJacketProductCategory.updatedAt
                        )
                )
                .from(bubbleJacketProductCategory)
                .where(
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(bubbleJacketProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return bubbleJacketProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return bubbleJacketProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = bubbleJacketProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = bubbleJacketProductCategory.price.goe(price1).and(bubbleJacketProductCategory.price.loe(price2));
        } else {
            goePrice = bubbleJacketProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
