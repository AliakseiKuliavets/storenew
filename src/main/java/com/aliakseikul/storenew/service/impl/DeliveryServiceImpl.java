package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.Delivery;
import com.aliakseikul.storenew.exeption.exeptions.DeliveryNotFoundException;
import com.aliakseikul.storenew.exeption.message.ErrorMessage;
import com.aliakseikul.storenew.repository.DeliveryRepository;
import com.aliakseikul.storenew.service.interf.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Override
    public Delivery findById(String id) {
        checkIdLength(id);
        return deliveryRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    public Delivery addDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void changeAddressById(String deliveryId, String deliveryAddress) {
        checkId(deliveryId);
        deliveryRepository.changeAddressById(UUID.fromString(deliveryId), deliveryAddress);
    }

    @Override
    public void deleteDeliveryById(String deliveryId) {
        deliveryRepository.deleteById(UUID.fromString(deliveryId));
    }

    private void checkId(String deliveryId) {
        if (findById(deliveryId) == null) {
            throw new DeliveryNotFoundException(ErrorMessage.DELIVERY_NOT_FOUND);
        }
    }

    private void checkIdLength(String deliveryId) {
        if (deliveryId.isEmpty()) {
            throw new DeliveryNotFoundException(ErrorMessage.WRONG_ID);
        }
        if (deliveryId.length() != 36) {
            throw new DeliveryNotFoundException(ErrorMessage.WRONG_ID_LENGTH);
        }
    }
}
