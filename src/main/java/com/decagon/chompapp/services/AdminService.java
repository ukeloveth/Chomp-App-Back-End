package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.models.ProductImage;
import org.springframework.http.ResponseEntity;

public interface AdminService {

    ResponseEntity<String> createProduct(ProductDto productDto);
    ResponseEntity<ProductImage> saveProductImage(String productImageURL, Long productId);
}
