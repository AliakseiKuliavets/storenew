package com.aliakseikul.storenew.repository;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("select p from Product p where p.productName = :name")
    List<Product> findByName(String name);

    @Query("select p from Product p where p.productCategory = :productCategory")
    List<Product> getAllProductsByCategory(ProductCategory productCategory);

    @Query("select p from Product p where p.productBrand = :productBrand")
    List<Product> getAllProductsByBrand(ProductBrand productBrand);

    @Query("select p from Product p where p.productPrice >= :minPrice and p.productPrice < :maxPrice")
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    @Query("select p from Product p where p.productCategory = :category and p.productBrand = :brand")
    List<Product> findByCategoryBrand(ProductCategory category, ProductBrand brand);

    @Query("select p from Product p " +
            "where p.productCategory = :category and p.productBrand = :brand and p.productName = :name")
    List<Product> searchProductsByCategoryBrandAndName(
            ProductCategory category, ProductBrand brand, String name);

    @Modifying
    @Query("update Product p set p.productName = :name where p.productId = :uuid")
    void updateProductNameWithId(UUID uuid, String name);
}
