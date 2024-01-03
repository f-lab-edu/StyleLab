package com.stylelab.category.presentation.response;

import com.stylelab.category.dto.ProductCategoriesDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class ProductCategoriesResponseTest {

    private List<ProductCategoriesDto> productCategoryDtos;

    @BeforeEach
    public void init() {
        productCategoryDtos = Arrays.asList(
                new ProductCategoriesDto("상의", "001001"),
                new ProductCategoriesDto("맨투맨/후드", "001001001", "001001"),
                new ProductCategoriesDto("맨투맨", "001001001001", "001001001"),
                new ProductCategoriesDto("후드", "001001001002", "001001001"),
                new ProductCategoriesDto("니트/스웨터", "001001002", "001001"),
                new ProductCategoriesDto("니트", "001001002001", "001001002"),
                new ProductCategoriesDto("스웨터", "001001002002", "001001002"),
                new ProductCategoriesDto("반소매 티셔츠", "001001003", "001001"),
                new ProductCategoriesDto("셔츠", "001001004", "001001"),
                new ProductCategoriesDto("바지", "001002"),
                new ProductCategoriesDto("데님 팬츠", "001002001", "001002"),
                new ProductCategoriesDto("코튼 팬츠", "001002002", "001002"),
                new ProductCategoriesDto("슬랙스", "001002003", "001002")
        );
    }

    @Test
    @DisplayName("카테고리 트리를 재귀적으로 생성합니다.")
    public void generateCategoryTreeRecursively() {

        List<ProductCategoriesResponse.Categories> parentCategories = productCategoryDtos.stream()
                .filter(productCategoriesDto -> !StringUtils.hasText(productCategoriesDto.parentCategory()))
                .map(ProductCategoriesResponse.Categories::of)
                .collect(Collectors.toList());

        generateCategoryTreeRecursively(productCategoryDtos, parentCategories);

        assertEquals(2, parentCategories.size());
        assertEquals(4, parentCategories.get(0).childCategories().size());
        assertEquals(2, parentCategories.get(0).childCategories().get(0).childCategories().size());
        assertEquals(0, parentCategories.get(0).childCategories().get(0).childCategories().get(0).childCategories().size());
        assertEquals(0, parentCategories.get(0).childCategories().get(0).childCategories().get(1).childCategories().size());
        assertEquals(2, parentCategories.get(0).childCategories().get(1).childCategories().size());
        assertEquals(0, parentCategories.get(0).childCategories().get(1).childCategories().get(0).childCategories().size());
        assertEquals(0, parentCategories.get(0).childCategories().get(1).childCategories().get(1).childCategories().size());
        assertEquals(0, parentCategories.get(0).childCategories().get(2).childCategories().size());
        assertEquals(0, parentCategories.get(0).childCategories().get(3).childCategories().size());
        assertEquals(3, parentCategories.get(1).childCategories().size());
        assertEquals(0, parentCategories.get(1).childCategories().get(0).childCategories().size());
        assertEquals(0, parentCategories.get(1).childCategories().get(1).childCategories().size());
        assertEquals(0, parentCategories.get(1).childCategories().get(2).childCategories().size());
    }

    private void generateCategoryTreeRecursively(
            List<ProductCategoriesDto> productCategoryDtos, List<ProductCategoriesResponse.Categories> categories) {
        for (ProductCategoriesResponse.Categories category : categories) {
            List<ProductCategoriesResponse.Categories> childCategories = productCategoryDtos.stream()
                    .filter(productCategoriesDto -> Objects.equals(productCategoriesDto.parentCategory(), category.categoryPath()))
                    .map(ProductCategoriesResponse.Categories::of)
                    .collect(Collectors.toList());
            category.addAllChildCategories(childCategories);
            generateCategoryTreeRecursively(productCategoryDtos, childCategories);
        }
    }
}
