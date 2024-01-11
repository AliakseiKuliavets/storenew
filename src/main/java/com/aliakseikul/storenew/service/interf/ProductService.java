package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.entity.Product;

import java.util.List;

public interface ProductService {

    Product findById(String id);

    List<Product> getAllProducts();
}
