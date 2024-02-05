package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.enums.PaymentMethod;
import com.aliakseikul.storenew.entity.enums.StatusTracking;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    @NotNull(message = "DeliveryAddress name shouldn't be null")
    @Size(min = 1, max = 44, message = "DeliveryAddress should be not null and from 1 to 44 symbols")
    String deliveryAddress;

    @NotNull(message = "PaymentMethod name shouldn't be null")
    @Size(min = 1, max = 44, message = "PaymentMethod should be not null and from 1 to 44 symbols")
    PaymentMethod paymentMethod;

    @NotNull(message = "StatusTracking name shouldn't be null")
    @Size(min = 1, max = 44, message = "StatusTracking should be not null and from 1 to 44 symbols")
    StatusTracking deliveryStatusTracking;
}
