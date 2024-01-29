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

import static com.stylelab.category.domain.shoes.QSneakersProductCategory.sneakersProductCategory;

@Repository
@RequiredArgsConstructor
public class SneakersProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                sneakersProductCategory.id,
                                sneakersProductCategory.productId,
                                sneakersProductCategory.storeId,
                                sneakersProductCategory.storeName,
                                sneakersProductCategory.productCategoryPath,
                                sneakersProductCategory.productCategoryName,
                                sneakersProductCategory.productMainImage,
                                sneakersProductCategory.productMainImageType,
                                sneakersProductCategory.name,
                                sneakersProductCategory.price,
                                sneakersProductCategory.discountPrice,
                                sneakersProductCategory.discountRate,
                                sneakersProductCategory.soldOut,
                                sneakersProductCategory.deleted,
                                sneakersProductCategory.createdAt,
                                sneakersProductCategory.updatedAt
                        )
                )
                .from(sneakersProductCategory)
                .where(
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(sneakersProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return sneakersProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return sneakersProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = sneakersProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = sneakersProductCategory.price.goe(price1).and(sneakersProductCategory.price.loe(price2));
        } else {
            goePrice = sneakersProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
