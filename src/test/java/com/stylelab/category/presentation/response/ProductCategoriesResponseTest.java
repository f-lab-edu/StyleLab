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

    private List<ProductCategoriesDto> productCategoriesDtos;

    @BeforeEach
    public void init() {
        productCategoriesDtos = Arrays.asList(
                ProductCategoriesDto.builder()
                        .categoryName("상의")
                        .categoryPath("001001")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("맨투맨/후드")
                        .categoryPath("001001001")
                        .parentCategory("001001")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("맨투맨")
                        .categoryPath("001001001001")
                        .parentCategory("001001001")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("후드")
                        .categoryPath("001001001002")
                        .parentCategory("001001001")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("니트/스웨터")
                        .categoryPath("001001002")
                        .parentCategory("001001")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("니트")
                        .categoryPath("001001002001")
                        .parentCategory("001001002")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("스웨터")
                        .categoryPath("001001002002")
                        .parentCategory("001001002")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("반소매 티셔츠")
                        .categoryPath("001001003")
                        .parentCategory("001001")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("셔츠")
                        .categoryPath("001001004")
                        .parentCategory("001001")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("바지")
                        .categoryPath("001002")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("데님 팬츠")
                        .categoryPath("001002001")
                        .parentCategory("001002")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("코튼 팬츠")
                        .categoryPath("001002002")
                        .parentCategory("001002")
                        .build(),
                ProductCategoriesDto.builder()
                        .categoryName("슬랙스")
                        .categoryPath("001002003")
                        .parentCategory("001002")
                        .build()
        );
    }

    @Test
    @DisplayName("카테고리 트리를 재귀적으로 생성합니다.")
    public void generateCategoryTreeRecursively() {

        List<ProductCategoriesResponse.Categories> parentCategories = productCategoriesDtos.stream()
                .filter(productCategoriesDto -> !StringUtils.hasText(productCategoriesDto.getParentCategory()))
                .map(ProductCategoriesResponse.Categories::of)
                .collect(Collectors.toList());

        generateCategoryTreeRecursively(productCategoriesDtos, parentCategories);

        assertEquals(2, parentCategories.size());
        assertEquals(4, parentCategories.get(0).getChildCategories().size());
        assertEquals(2, parentCategories.get(0).getChildCategories().get(0).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(0).getChildCategories().get(0).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(0).getChildCategories().get(1).getChildCategories().size());
        assertEquals(2, parentCategories.get(0).getChildCategories().get(1).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(1).getChildCategories().get(0).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(1).getChildCategories().get(1).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(2).getChildCategories().size());
        assertEquals(0, parentCategories.get(0).getChildCategories().get(3).getChildCategories().size());
        assertEquals(3, parentCategories.get(1).getChildCategories().size());
        assertEquals(0, parentCategories.get(1).getChildCategories().get(0).getChildCategories().size());
        assertEquals(0, parentCategories.get(1).getChildCategories().get(1).getChildCategories().size());
        assertEquals(0, parentCategories.get(1).getChildCategories().get(2).getChildCategories().size());
    }

    private void generateCategoryTreeRecursively(
            List<ProductCategoriesDto> productCategoriesDtos, List<ProductCategoriesResponse.Categories> categories) {
        for (ProductCategoriesResponse.Categories category : categories) {
            List<ProductCategoriesResponse.Categories> childCategories = productCategoriesDtos.stream()
                    .filter(productCategoriesDto -> Objects.equals(productCategoriesDto.getParentCategory(), category.getCategoryPath()))
                    .map(ProductCategoriesResponse.Categories::of)
                    .collect(Collectors.toList());
            category.addAllChildCategories(childCategories);
            generateCategoryTreeRecursively(productCategoriesDtos, childCategories);
        }
    }
}
