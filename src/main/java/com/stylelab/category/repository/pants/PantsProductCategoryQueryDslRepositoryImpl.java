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

import static com.stylelab.category.domain.pants.QPantsProductCategory.pantsProductCategory;

@Repository
@RequiredArgsConstructor
public class PantsProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                pantsProductCategory.id,
                                pantsProductCategory.productId,
                                pantsProductCategory.storeId,
                                pantsProductCategory.storeName,
                                pantsProductCategory.productCategoryPath,
                                pantsProductCategory.productCategoryName,
                                pantsProductCategory.productMainImage,
                                pantsProductCategory.productMainImageType,
                                pantsProductCategory.name,
                                pantsProductCategory.price,
                                pantsProductCategory.discountPrice,
                                pantsProductCategory.discountRate,
                                pantsProductCategory.soldOut,
                                pantsProductCategory.deleted,
                                pantsProductCategory.createdAt,
                                pantsProductCategory.updatedAt
                        )
                )
                .from(pantsProductCategory)
                .where(
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(pantsProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return pantsProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return pantsProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = pantsProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = pantsProductCategory.price.goe(price1).and(pantsProductCategory.price.loe(price2));
        } else {
            goePrice = pantsProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
