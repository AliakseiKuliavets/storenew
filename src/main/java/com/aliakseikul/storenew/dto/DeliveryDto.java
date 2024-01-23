package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.enums.PaymentMethod;
import com.aliakseikul.storenew.entity.enums.StatusTracking;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    String deliveryAddress;
    PaymentMethod paymentMethod;
    StatusTracking deliveryStatusTracking;
}
