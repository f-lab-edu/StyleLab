package com.stylelab.category.repository.pants;

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

import static com.stylelab.category.domain.pants.QDenimPantsProductCategory.denimPantsProductCategory;

@Repository
@RequiredArgsConstructor
public class DenimPantsProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                denimPantsProductCategory.id,
                                denimPantsProductCategory.productId,
                                denimPantsProductCategory.storeId,
                                denimPantsProductCategory.storeName,
                                denimPantsProductCategory.productCategoryPath,
                                denimPantsProductCategory.productCategoryName,
                                denimPantsProductCategory.productMainImage,
                                denimPantsProductCategory.productMainImageType,
                                denimPantsProductCategory.name,
                                denimPantsProductCategory.price,
                                denimPantsProductCategory.discountPrice,
                                denimPantsProductCategory.discountRate,
                                denimPantsProductCategory.soldOut,
                                denimPantsProductCategory.deleted,
                                denimPantsProductCategory.createdAt,
                                denimPantsProductCategory.updatedAt
                        )
                )
                .from(denimPantsProductCategory)
                .where(
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(denimPantsProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return denimPantsProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return denimPantsProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = denimPantsProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = denimPantsProductCategory.price.goe(price1).and(denimPantsProductCategory.price.loe(price2));
        } else {
            goePrice = denimPantsProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
