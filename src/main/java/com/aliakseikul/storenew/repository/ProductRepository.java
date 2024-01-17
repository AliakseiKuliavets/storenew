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
    @Query("select p from Product p")
    List<Product> getAllProducts();

    @Query("select p from Product p where p.productName = :name")
    Product findByName(String name);

    @Query("select p from Product p where p.productCategory = :productCategory")
    List<Product> getAllProductsByCategory(ProductCategory productCategory);

    @Query("select p from Product p where p.productBrand = :productBrand")
    List<Product> getAllProductsByBrand(ProductBrand productBrand);

    @Query("select p from Product p where p.productPrice >= :minPrice and p.productPrice < :maxPrice")
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    @Query("select p from Product p where p.productCategory = :category and p.productBrand = :brand")
    List<Product> findByCategoryBrand(ProductCategory category, ProductBrand brand);

    @Modifying
    @Query("update Product p set p.productName = :name where p.productId = :productId")
    void updateProductName(UUID productId, String name);

    @Modifying
    @Query("update Product p set p.productPrice = :price where p.productId = :productId")
    void updateProductPrice(UUID productId, String price);

    @Modifying
    @Query("update Product p set p.productDescription = :descriptions where p.productId = :productId")
    void updateProductDescriptions(UUID productId, String descriptions);

    @Modifying
    @Query("update Product p set p.productCategory = :category where p.productId = :productId")
    void updateProductCategory(UUID productId, ProductCategory category);

    @Modifying
    @Query("update Product p set p.productBrand = :brand where p.productId = :productId")
    void updateProductBrand(UUID productId, ProductBrand brand);
}
