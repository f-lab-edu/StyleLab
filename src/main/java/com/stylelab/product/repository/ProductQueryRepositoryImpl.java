package com.stylelab.product.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.stylelab.file.constant.ImageType;
import com.stylelab.product.repository.dto.ProductCollection;
import com.stylelab.product.repository.dto.QProductCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.stylelab.category.domain.QProductCategories.productCategories;
import static com.stylelab.product.domain.QProduct.product;
import static com.stylelab.product.domain.QProductImage.productImage;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ProductCollection> findByProductByConditions(
            String productName, String productCategoryPath, Integer price1, Integer price2, Integer discountRate, Pageable pageable) {

        List<ProductCollection> items = jpaQueryFactory
                .select(
                        new QProductCollection(
                                product.productId,
                                product.name,
                                productCategories.categoryPath,
                                productCategories.categoryName,
                                product.price,
                                product.discountPrice,
                                product.discountRate,
                                productImage.imageUrl,
                                productImage.imageType
                        )
                )
                .from(product)
                .innerJoin(productImage).on(
                        product.productId.eq(productImage.product.productId)
                                .and(productImage.imageType.eq(ImageType.PRODUCT_ENTRY_MAIN))
                )
                .innerJoin(productCategories).on(product.productCategoryPath.eq(productCategories.categoryPath))
                .where(
                        likeProductCategoryPath(productCategoryPath),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(product.count())
                .from(product)
                .where(
                        likeProductCategoryPath(productCategoryPath),
                        eqDiscountRate(discountRate),
                        betweenPrice(price1, price2)
                );

        return PageableExecutionUtils.getPage(
                items,
                pageable,
                count::fetchOne
        );
    }

    private BooleanExpression likeProductCategoryPath(String productCategoryPath) {
        if (!StringUtils.hasText(productCategoryPath)) {
            return null;
        }

        return product.productCategoryPath.like(productCategoryPath + "%");
    }

    private BooleanExpression eqDiscountRate(Integer discountRate) {
        if (discountRate == null) {
            return null;
        }

        return product.discountRate.eq(discountRate);
    }

    private BooleanExpression betweenPrice(Integer price1, Integer price2) {
        if (price1 == null && price2 == null) {
            return null;
        }

        BooleanExpression goePrice;
        if (price1 != null && price2 == null) {
            goePrice = product.price.goe(price1);
        } else if (price1 != null) {
            goePrice = product.price.goe(price1).and(product.price.loe(price2));
        } else {
            goePrice = product.price.loe(price2);
        }

        return goePrice;
    }
}
