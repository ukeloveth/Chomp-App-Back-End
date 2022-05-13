package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.models.Category;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ProductServicesImplTests {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServicesImpl productServicesImpl;

    @Test
    void getAllProducts() {
        Category burger = Category.builder().categoryId(1L).categoryName("Burger").build();
        Product product = Product.builder().productId(1L).productName("Product 1").category(burger).build();
        Pageable pageable = PageRequest.of(0, 2, Sort.by("productId"));
        List<Product> listOfProducts = new ArrayList<>();
        listOfProducts.add(product);
        Page<Product> products = new PageImpl<>(listOfProducts);
        Mockito.when(productRepository.findAll(pageable)).thenReturn(products);
        String sortBy = "productId";
        String sortDir = "asc";
        String filterBy = "";
        String filterParam = "";
        String productPriceStartRange= "";
        String productPriceEndRange= "";
        productServicesImpl.getAllProducts(0, 2, sortBy, sortDir, filterBy, filterParam,productPriceStartRange,productPriceEndRange);
        verify(productRepository).findAll(pageable);
    }
}
