package com.decagon.chompapp.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long productId;

    private String productName;

    private String size;

    private int quantity;

    private double productPrice;

    private String productImage;

    private String categoryName;

}
