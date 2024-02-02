package com.stylelab.category.repository;

import com.stylelab.category.repository.dto.ProductCategoryCollection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@SpringBootTest
@ActiveProfiles(profiles = "local")
public class ProductCategoryJdbcRepositoryTest {

    @Autowired
    private JdbcClient jdbcClient;

    @Test
    @DisplayName("상의 카테고리 목록 조회")
    public void findAllTopProductCategoryConditionsJdbcTest() {
        String targetProductCategoryTable = "product_category_001001";
        int size = 10;
        Pageable pageable = Pageable.ofSize(size);
        String selectFrom = selectFrom(targetProductCategoryTable);

        Long productId = null;
        Integer discountRate = null;
        Integer price1 = null;
        Integer price2 = null;
        List<Object> params = new ArrayList<>();
        String where =
                addItProductId(productId, params) +
                addEqDiscountRate(discountRate, params) +
                addBetweenPrice(price1, price2, params);

        String orderBy = orderBy(pageable);
        String sql = selectFrom + where + orderBy;

        List<ProductCategoryCollection> content = jdbcClient.sql(sql)
                .params(params)
                .query(ProductCategoryCollection.class)
                .list();

        SliceImpl<ProductCategoryCollection> productCategoryCollections = new SliceImpl<>(content, pageable, isLast(pageable, content));
        for (ProductCategoryCollection productCategoryCollection : productCategoryCollections) {
            log.info("productCategoryCollection: {}", productCategoryCollection.name());
        }
    }

    private boolean isLast(Pageable pageable, List<ProductCategoryCollection> results) {
        boolean isLast = false;
        if (results.size() > pageable.getPageSize()) {
            isLast = true;
            results.remove(pageable.getPageSize());
        }

        return isLast;
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
            goePrice = "price >= ?";
        } else if (price1 != null) {
            params.add(price1);
            params.add(price2);
            goePrice = "price >= ? and price <= p2";
        } else {
            params.add(price2);
            goePrice = "price <= ?";
        }

        return goePrice;
    }
}
