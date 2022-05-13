package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.exceptions.ProductNotFoundException;
import com.decagon.chompapp.models.Category;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.ProductImage;
import com.decagon.chompapp.repositories.CategoryRepository;
import com.decagon.chompapp.repositories.ProductImageRepository;
import com.decagon.chompapp.repositories.ProductRepository;
import com.decagon.chompapp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;


    Product product = new Product();


    @Autowired
    public AdminServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }


    @Override
    public ResponseEntity<String> createProduct(ProductDto productDto) {
        product = new Product();
        Optional<Category> productCategory = categoryRepository.findCategoryByCategoryName(productDto.getCategoryName().toLowerCase());
        Category category;
        if (productCategory.isEmpty()) {
            category = new Category();
            category.setCategoryName(productDto.getCategoryName());
            categoryRepository.save(category);
            product.setCategory(category);
        } else {
            category = productCategory.get();
            product.setCategory(category);
        }
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setSize(productDto.getSize());
        product.setQuantity(productDto.getQuantity());
        Product savedProduct = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully with product id " + savedProduct.getProductId());

    }

    @Override
    public ResponseEntity<ProductImage> saveProductImage(String productImageURL, Long productId) {
        ProductImage productImage = new ProductImage();
        productImage.setImageURL(productImageURL);
        Optional<Product> createdProduct = productRepository.findProductByProductId(productId);
        if (createdProduct.isPresent()) {
            productImage.setProduct(createdProduct.get());
            product.setProductImage(productImage.getImageURL());
        } else {
            throw new ProductNotFoundException("productName", "productId", productId);
        }
        createdProduct.get().setProductImage(productImage.getImageURL());
        productRepository.save(createdProduct.get());
        return ResponseEntity.ok(productImageRepository.save(productImage));
    }
}
