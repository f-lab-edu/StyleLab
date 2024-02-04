package com.stylelab.product.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("상품 목록 조회 테스트")
    public class FindByProductByConditionsTest {

        @Test
        @DisplayName("상품 목록 조회 성공")
        public void successFindByProductByConditionsTest() throws Exception {
            //given

            //when
            mockMvc.perform(get("/v1/products")
                            .param("page", "0")
                            .param("size", "10")
                            .param("productCategoryPath", "001001002")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    //then
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value("20000"))
                    .andExpect(jsonPath("$.message").value("success"));
        }
    }
}
