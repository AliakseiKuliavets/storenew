package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @Column(name = "product_brand")
    @Enumerated(EnumType.STRING)
    private ProductBrand productBrand;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "placed_by_user", referencedColumnName = "user_id")
    private User placedByUser;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "purchased_by_user", referencedColumnName = "user_id")
    private User purchasedByUser;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
//    mappedBy = "product")
//    private List<Image> images = new ArrayList<>();
//    private Long previewImageId;

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
//                ", images=" + images +
//                ", previewImageId=" + previewImageId +
                '}';
    }
}
