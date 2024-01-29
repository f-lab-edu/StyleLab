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

import static com.stylelab.category.domain.shoes.QCanvasAndSneakersProductCategory.canvasAndSneakersProductCategory;

@Repository
@RequiredArgsConstructor
public class CanvasAndSneakersProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                canvasAndSneakersProductCategory.id,
                                canvasAndSneakersProductCategory.productId,
                                canvasAndSneakersProductCategory.storeId,
                                canvasAndSneakersProductCategory.storeName,
                                canvasAndSneakersProductCategory.productCategoryPath,
                                canvasAndSneakersProductCategory.productCategoryName,
                                canvasAndSneakersProductCategory.productMainImage,
                                canvasAndSneakersProductCategory.productMainImageType,
                                canvasAndSneakersProductCategory.name,
                                canvasAndSneakersProductCategory.price,
                                canvasAndSneakersProductCategory.discountPrice,
                                canvasAndSneakersProductCategory.discountRate,
                                canvasAndSneakersProductCategory.soldOut,
                                canvasAndSneakersProductCategory.deleted,
                                canvasAndSneakersProductCategory.createdAt,
                                canvasAndSneakersProductCategory.updatedAt
                        )
                )
                .from(canvasAndSneakersProductCategory)
                .where(
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(canvasAndSneakersProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return canvasAndSneakersProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return canvasAndSneakersProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = canvasAndSneakersProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = canvasAndSneakersProductCategory.price.goe(price1).and(canvasAndSneakersProductCategory.price.loe(price2));
        } else {
            goePrice = canvasAndSneakersProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
