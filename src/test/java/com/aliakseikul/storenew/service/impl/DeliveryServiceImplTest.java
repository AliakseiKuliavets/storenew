package com.aliakseikul.storenew.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImplTest {

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @Test
    void addDelivery() {
        assertThrows(NullPointerException.class, () -> deliveryService.addDelivery(null));
    }
}
