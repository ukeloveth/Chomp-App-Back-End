package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.models.Category;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.ProductImage;
import com.decagon.chompapp.repositories.CategoryRepository;
import com.decagon.chompapp.repositories.ProductImageRepository;
import com.decagon.chompapp.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductImageRepository productImageRepository;



    @InjectMocks
    private AdminServiceImpl adminService;

    ProductDto productDto;
    Product product;
    Category category;
    ProductImage productImage;

    @BeforeEach
    void setUp() {

        productDto = ProductDto.builder()
                .productId(1L)
                .productPrice(200)
                .productName("Pizza")
                .categoryName("sides")
                .quantity(2L)
                .size("Big")
                .build();

        product = Product.builder()
                .productId(productDto.getProductId())
                .productName(productDto.getProductName())
                .quantity(productDto.getQuantity())
                .size(productDto.getSize())
                .productPrice(productDto.getProductPrice())
                .category(category)
                .build();

        category = Category.builder()
                .categoryName("sides")
                .build();

        productImage = ProductImage.builder()
                .productImageId(1L)
                .imageURL("http://res.cloudinary.com/deenn/image/upload/v1652303973/gcljvikqbxofsgkr11cl.png")
                .build();

    }

    @Test
    void createProduct() {

        when(categoryRepository.findCategoryByCategoryName(any())).thenReturn(Optional.of(category));
        when(productRepository.save(any())).thenReturn(product);
        ResponseEntity<String > savedProduct = adminService.createProduct(productDto);
        org.assertj.core.api.Assertions.assertThat(savedProduct.getBody()).isNotNull();

        Assertions.assertEquals(savedProduct.getBody(),   "Product created successfully with product id "+ product.getProductId());
        Assertions.assertEquals(savedProduct.getStatusCode(), HttpStatus.CREATED);

        verify(categoryRepository, atLeastOnce()).findCategoryByCategoryName(any());
        verify(productRepository, atLeastOnce()).save(any());


    }

    @Test
    void saveProductImage() {
        String url = "";
        when(productRepository.findProductByProductId(product.getProductId())).thenReturn(Optional.of(product));
        when(productImageRepository.save(any())).thenReturn(productImage);
        ResponseEntity<ProductImage> productImageResponseEntity = adminService.saveProductImage(url, product.getProductId());
        Assertions.assertEquals(productImageResponseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(productImageResponseEntity.getBody(), productImage);
        Assertions.assertEquals(productImageResponseEntity.getBody().getImageURL() , productImage.getImageURL());
        verify(productRepository, atLeastOnce()).findProductByProductId(any());
        verify(productImageRepository, atLeastOnce()).save(any());

    }
}