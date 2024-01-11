package com.aliakseikul.storenew.repository;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("select p from Product p")
    List<Product> getAllProducts();

    @Query("select p from Product p where p.productCategory = :productCategory")
    List<Product> getAllProductsByCategory(ProductCategory productCategory);
}
