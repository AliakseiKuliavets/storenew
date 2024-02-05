package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.dto.DeliveryDto;
import com.aliakseikul.storenew.entity.Delivery;
import com.aliakseikul.storenew.exception.exeptions.DeliveryNotFoundException;
import com.aliakseikul.storenew.exception.message.ErrorMessage;
import com.aliakseikul.storenew.mapper.DeliveryMapper;
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

    private final DeliveryMapper deliveryMapper;

    @Override
    public DeliveryDto findById(String id) {
        return deliveryMapper.toDto(deliveryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new DeliveryNotFoundException(ErrorMessage.DELIVERY_NOT_FOUND)));
    }

    @Override
    public DeliveryDto addDelivery(DeliveryDto deliveryDto) {
        if (deliveryDto == null) {
            throw new NullPointerException(ErrorMessage.NULL_OR_EMPTY);
        }

        Delivery delivery = Delivery.builder()
                .deliveryAddress(deliveryDto.getDeliveryAddress())
                .deliveryStatusTracking(deliveryDto.getDeliveryStatusTracking())
                .paymentMethod(deliveryDto.getPaymentMethod())
                .build();

        return deliveryMapper.toDto(deliveryRepository.save(delivery));
    }

    @Override
    @Transactional
    public void changeAddressById(String deliveryId, String deliveryAddress) {
        findById(deliveryId);
        deliveryRepository.changeAddressById(UUID.fromString(deliveryId), deliveryAddress);
    }

    @Override
    public void deleteDeliveryById(String deliveryId) {
        findById(deliveryId);
        deliveryRepository.deleteById(UUID.fromString(deliveryId));
    }
}
