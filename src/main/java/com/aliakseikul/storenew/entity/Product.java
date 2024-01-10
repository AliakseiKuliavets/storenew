package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_category")
    private ProductCategory productCategory;

    @Column(name = "product_brand")
    private ProductBrand productBrand;

    @ManyToOne
    @JoinColumn(name = "placed_by_user", referencedColumnName = "user_id")
    private User placedByUser;

    @ManyToOne
    @JoinColumn(name = "purchased_by_user", referencedColumnName = "user_id")
    private User purchasedByUser;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    mappedBy = "product")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
}
