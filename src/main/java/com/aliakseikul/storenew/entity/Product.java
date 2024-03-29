package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @Column(name = "product_brand")
    @Enumerated(EnumType.STRING)
    private ProductBrand productBrand;

    @Column(name = "date_of_create")
    private Date dateOfCreate;

    @Column(name = "preview_image_id")
    private Long previewImageId;

    @ManyToOne
    @JsonBackReference("placedUserReference")
    @JoinColumn(name = "placed_by_user", referencedColumnName = "user_id")
    private User placedByUser;

    @ManyToOne
    @JsonBackReference("purchasedUserReference")
    @JoinColumn(name = "purchased_by_user", referencedColumnName = "user_id")
    private User purchasedByUser;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productCategory=" + productCategory +
                ", productBrand=" + productBrand +
                ", placedByUser=" + placedByUser +
                ", purchasedByUser=" + purchasedByUser +
                '}';
    }
}

