package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Delivery;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.repository.DeliveryRepository;
import com.aliakseikul.storenew.repository.ProductRepository;
import com.aliakseikul.storenew.service.interf.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Override
    public Delivery findById(String id) {
        return deliveryRepository.findById(UUID.fromString(id)).orElse(null);
    }
}
