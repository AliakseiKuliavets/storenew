package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.entity.Delivery;
import org.springframework.http.ResponseEntity;

public interface DeliveryService {

    Delivery findById(String id);

    Delivery addDelivery(Delivery delivery);

    void changeAddressById(String deliveryId, String deliveryAddress);

    void deleteDeliveryById(String deliveryId);
}
