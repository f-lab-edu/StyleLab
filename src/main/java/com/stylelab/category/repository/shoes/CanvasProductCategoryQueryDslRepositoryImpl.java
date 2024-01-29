package com.stylelab.category.repository.shoes;

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

import static com.stylelab.category.domain.shoes.QCanvasProductCategory.canvasProductCategory;

@Repository
@RequiredArgsConstructor
public class CanvasProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                canvasProductCategory.id,
                                canvasProductCategory.productId,
                                canvasProductCategory.storeId,
                                canvasProductCategory.storeName,
                                canvasProductCategory.productCategoryPath,
                                canvasProductCategory.productCategoryName,
                                canvasProductCategory.productMainImage,
                                canvasProductCategory.productMainImageType,
                                canvasProductCategory.name,
                                canvasProductCategory.price,
                                canvasProductCategory.discountPrice,
                                canvasProductCategory.discountRate,
                                canvasProductCategory.soldOut,
                                canvasProductCategory.deleted,
                                canvasProductCategory.createdAt,
                                canvasProductCategory.updatedAt
                        )
                )
                .from(canvasProductCategory)
                .where(
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(canvasProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return canvasProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return canvasProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = canvasProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = canvasProductCategory.price.goe(price1).and(canvasProductCategory.price.loe(price2));
        } else {
            goePrice = canvasProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
