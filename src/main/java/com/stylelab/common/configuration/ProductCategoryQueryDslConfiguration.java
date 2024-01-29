package com.stylelab.common.configuration;

import com.stylelab.category.constant.ProductCategoryType;
import com.stylelab.category.repository.ProductCategoryQueryDslRepository;
import com.stylelab.category.repository.ProductCategoryQueryDslRepositoryStrategyMap;
import com.stylelab.category.repository.outer.BubbleJacketProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.outer.OuterProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.pants.DenimPantsProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.pants.PantsProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.shoes.CanvasAndSneakersProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.shoes.CanvasProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.shoes.ShoesProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.shoes.SneakersProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.top.HoodieAndSweatshirtProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.top.HoodieProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.top.SweatshirtProductCategoryQueryDslRepositoryImpl;
import com.stylelab.category.repository.top.TopProductCategoryQueryDslRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ProductCategoryQueryDslConfiguration {

    private final TopProductCategoryQueryDslRepositoryImpl topProductCategoryQueryDslRepository;
    private final HoodieAndSweatshirtProductCategoryQueryDslRepositoryImpl hoodieAndSweatshirtProductCategoryQueryDslRepository;
    private final SweatshirtProductCategoryQueryDslRepositoryImpl sweatshirtProductCategoryQueryDslRepository;
    private final HoodieProductCategoryQueryDslRepositoryImpl hoodieProductCategoryQueryDslRepository;
    private final ShoesProductCategoryQueryDslRepositoryImpl shoesProductCategoryQueryDslRepository;
    private final CanvasAndSneakersProductCategoryQueryDslRepositoryImpl canvasAndSneakersProductCategoryQueryDslRepository;
    private final CanvasProductCategoryQueryDslRepositoryImpl canvasProductCategoryQueryDslRepository;
    private final SneakersProductCategoryQueryDslRepositoryImpl sneakersProductCategoryQueryDslRepository;
    private final PantsProductCategoryQueryDslRepositoryImpl pantsProductCategoryQueryDslRepository;
    private final DenimPantsProductCategoryQueryDslRepositoryImpl denimPantsProductCategoryQueryDslRepository;
    private final OuterProductCategoryQueryDslRepositoryImpl outerProductCategoryQueryDslRepository;
    private final BubbleJacketProductCategoryQueryDslRepositoryImpl bubbleJacketProductCategoryQueryDslRepository;

    @Bean
    public ProductCategoryQueryDslRepositoryStrategyMap productCategoryQueryRepositoryStrategyMap() {
        Map<ProductCategoryType, ProductCategoryQueryDslRepository> productCategoryRepositoryMap = new EnumMap<>(ProductCategoryType.class);
        productCategoryRepositoryMap.put(ProductCategoryType.TOP, topProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.SWEATSHIRT_AND_HOODIE, hoodieAndSweatshirtProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.HOODIE, hoodieProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.SHOES, shoesProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.CANVAS_AND_SNEAKERS, canvasAndSneakersProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.CANVAS, canvasProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.SNEAKERS, sneakersProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.PANTS, pantsProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.DENIM_PANTS, denimPantsProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.OUTER, outerProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.SWEATSHIRT, sweatshirtProductCategoryQueryDslRepository);
        productCategoryRepositoryMap.put(ProductCategoryType.BUBBLE_JACKET, bubbleJacketProductCategoryQueryDslRepository);

        return ProductCategoryQueryDslRepositoryStrategyMap.builder()
                .productCategoryRepositoryMap(productCategoryRepositoryMap)
                .build();
    }
}
