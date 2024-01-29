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

import static com.stylelab.category.domain.outer.QOuterProductCategory.outerProductCategory;

@Repository
@RequiredArgsConstructor
public class OuterProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                outerProductCategory.id,
                                outerProductCategory.productId,
                                outerProductCategory.storeId,
                                outerProductCategory.storeName,
                                outerProductCategory.productCategoryPath,
                                outerProductCategory.productCategoryName,
                                outerProductCategory.productMainImage,
                                outerProductCategory.productMainImageType,
                                outerProductCategory.name,
                                outerProductCategory.price,
                                outerProductCategory.discountPrice,
                                outerProductCategory.discountRate,
                                outerProductCategory.soldOut,
                                outerProductCategory.deleted,
                                outerProductCategory.createdAt,
                                outerProductCategory.updatedAt
                        )
                )
                .from(outerProductCategory)
                .where(
                    ltProductId(productId),
                    eqDiscountRate(discountRate),
                    betweenPrice(price1, price2)
                )
                .orderBy(outerProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return outerProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return outerProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = outerProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = outerProductCategory.price.goe(price1).and(outerProductCategory.price.loe(price2));
        } else {
            goePrice = outerProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
