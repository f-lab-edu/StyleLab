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

import static com.stylelab.category.domain.shoes.QShoesProductCategory.shoesProductCategory;

@Repository
@RequiredArgsConstructor
public class ShoesProductCategoryQueryDslRepositoryImpl implements ProductCategoryQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(
            Long productId, String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {
        List<ProductCategoryCollection> productCategoryCollections = jpaQueryFactory
                .select(
                        new QProductCategoryCollection(
                                shoesProductCategory.id,
                                shoesProductCategory.productId,
                                shoesProductCategory.storeId,
                                shoesProductCategory.storeName,
                                shoesProductCategory.productCategoryPath,
                                shoesProductCategory.productCategoryName,
                                shoesProductCategory.productMainImage,
                                shoesProductCategory.productMainImageType,
                                shoesProductCategory.name,
                                shoesProductCategory.price,
                                shoesProductCategory.discountPrice,
                                shoesProductCategory.discountRate,
                                shoesProductCategory.soldOut,
                                shoesProductCategory.deleted,
                                shoesProductCategory.createdAt,
                                shoesProductCategory.updatedAt
                        )
                )
                .from(shoesProductCategory)
                .where(
                        ltProductId(productId),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .orderBy(shoesProductCategory.productId.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(productCategoryCollections, pageable, isLast(pageable, productCategoryCollections));
    }

    private BooleanExpression ltProductId(Long productId) {
        if (productId == null) {
            return null;
        }

        return shoesProductCategory.productId.lt(productId);
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return shoesProductCategory.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = shoesProductCategory.price.goe(price1);
        } else if (price1 != null) {
            goePrice = shoesProductCategory.price.goe(price1).and(shoesProductCategory.price.loe(price2));
        } else {
            goePrice = shoesProductCategory.price.loe(price2);
        }

        return goePrice;
    }
}
