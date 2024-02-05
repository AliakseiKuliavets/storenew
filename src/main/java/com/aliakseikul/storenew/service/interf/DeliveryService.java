package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.dto.DeliveryDto;

public interface DeliveryService {

    DeliveryDto findById(String id);

    DeliveryDto addDelivery(DeliveryDto deliveryDto);

    void changeAddressById(String deliveryId, String deliveryAddress);

    void deleteDeliveryById(String deliveryId);
}
