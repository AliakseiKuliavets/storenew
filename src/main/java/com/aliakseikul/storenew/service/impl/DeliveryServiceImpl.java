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

import static com.aliakseikul.storenew.exception.checkMethods.Check.checkIdLength;
import static com.aliakseikul.storenew.exception.checkMethods.Check.valueNullOrEmpty;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final DeliveryMapper deliveryMapper;

    @Override
    public DeliveryDto findById(String id) {
        if (checkIdLength(id)) {
            throw new DeliveryNotFoundException(ErrorMessage.WRONG_ID_LENGTH);
        }
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
        if (checkId(deliveryId)) {
            throw new DeliveryNotFoundException(ErrorMessage.DELIVERY_NOT_FOUND);
        }
        if (valueNullOrEmpty(deliveryAddress)) {
            throw new DeliveryNotFoundException(ErrorMessage.NULL_OR_EMPTY);
        }
        deliveryRepository.changeAddressById(UUID.fromString(deliveryId), deliveryAddress);
    }

    @Override
    public void deleteDeliveryById(String deliveryId) {
        if (checkId(deliveryId)) {
            throw new DeliveryNotFoundException(ErrorMessage.DELIVERY_NOT_FOUND);
        }
        deliveryRepository.deleteById(UUID.fromString(deliveryId));
    }

    private boolean checkId(String deliveryId) {
        if (valueNullOrEmpty(deliveryId)) {
            throw new DeliveryNotFoundException(ErrorMessage.NULL_OR_EMPTY);
        }
        return findById(deliveryId) == null;
    }
}
