package com.decagon.chompapp.controllers;

import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.ProductImage;
import com.decagon.chompapp.services.AdminService;
import com.decagon.chompapp.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/")
public class AdminServiceController {

    private final AdminService adminService;

    private final CloudinaryService cloudinaryService;


    @Autowired
    public AdminServiceController(AdminService adminService, CloudinaryService cloudinaryService) {
        this.adminService = adminService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("create-product")
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {
        return adminService.createProduct(productDto);
    }

    @PostMapping("upload-image")
    public ResponseEntity<ProductImage> uploadImage(@RequestPart MultipartFile image, @RequestParam("id") Product product){
        return adminService.saveProductImage(cloudinaryService.uploadFile(image), product.getProductId());
    }

    @PutMapping("update-product/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDto productDto, @PathVariable long id){
        return adminService.updateProduct(productDto, id);
    }

    @PutMapping("update-product-image/{id}")
    public ResponseEntity<ProductImage> updateProductImage(@RequestPart MultipartFile image,@PathVariable long id){
        return adminService.updateProductImage(cloudinaryService.uploadFile(image), id);
    }
}
