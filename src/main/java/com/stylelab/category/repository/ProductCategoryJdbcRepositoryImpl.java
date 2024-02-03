package com.stylelab.category.repository;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.category.dto.ProductCategoryCondition;
import com.stylelab.category.repository.dto.ProductCategoryCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCategoryJdbcRepositoryImpl implements ProductCategoryJdbcRepository {

    private final JdbcClient jdbcClient;

    @Override
    public Slice<ProductCategoryCollection> findAllProductCategoryConditions(ProductCategoryCondition productCategoryCondition) {
        Pageable pageable = productCategoryCondition.pageable();
        ProductCategoryType productCategoryType = productCategoryCondition.productCategoryType();
        String targetProductCategoryTable = productCategoryType.getTableName();

        List<Object> params = new ArrayList<>();
        String sql = selectFrom(targetProductCategoryTable);
        String where =
                addItProductId(productCategoryCondition.productId(), params) +
                        addEqDiscountRate(productCategoryCondition.discountRate(), params) +
                        addBetweenPrice(productCategoryCondition.price1(), productCategoryCondition.price2(), params);
        String orderBy = orderBy(pageable);

        List<ProductCategoryCollection> content = jdbcClient.sql(sql + where + orderBy)
                .params(params)
                .query(ProductCategoryCollection.class)
                .list();

        return new SliceImpl<>(content, pageable, isLast(pageable, content));
    }

    private static String selectFrom(String targetProductCategoryTable) {
        return """
                select
                    %s_id as product_category_id
                    , product_id
                    , store_id
                    , store_name
                    , product_category_path
                    , product_category_name
                    , name
                    , price
                    , discount_price
                    , discount_rate
                    , product_main_image
                    , product_main_image_type
                    , sold_out
                    , deleted
                    , created_at
                    , updated_at
                from %s
                where 1=1
                """.formatted(targetProductCategoryTable, targetProductCategoryTable);
    }

    private static String orderBy(Pageable pageable) {
        return """
                order by product_id desc
                limit %s
                """.formatted(pageable.getPageSize() + 1);
    }

    private String addItProductId(Long productId, List<? super Object> params) {
        if (productId == null) {
            return "";
        }

        params.add(productId);
        return "and product_id < ? \n";
    }

    private String addEqDiscountRate(Integer discountRate, List<? super Object> params) {
        if (discountRate == null) {
            return "";
        }

        params.add(discountRate);
        return "and discount_rate = ? \n";
    }

    private String addBetweenPrice(Integer price1, Integer price2, List<? super Object> params) {
        if (price1 == null && price2 == null) {
            return "";
        }

        String goePrice;
        if (price1 != null && price2 == null) {
            params.add(price1);
            goePrice = "and price >= ? \n";
        } else if (price1 != null) {
            params.add(price1);
            params.add(price2);
            goePrice = "and price >= ? and price <= ? \n";
        } else {
            params.add(price2);
            goePrice = "and price <= ? \n";
        }

        return goePrice;
    }
}
