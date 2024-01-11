package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.repository.ProductRepository;
import com.aliakseikul.storenew.service.interf.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product findById(String id) {
        return productRepository.findById(UUID.fromString(id)).orElse(null);
    }
}
