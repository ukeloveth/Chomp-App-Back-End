package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findAllByProductNameContains (Pageable pageable, String productName);

    Page<Product> findAllBySizeContains (Pageable pageable, String size);

    Page<Product> findAllByQuantityContains (Pageable pageable, String size);

    Page<Product> findAllByProductPriceContains (Pageable pageable, String size);

    Page<Product> findAllByCategory_CategoryName(Pageable pageable, String categoryName);


}
