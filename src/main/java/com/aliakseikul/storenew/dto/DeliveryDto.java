package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.enums.PaymentMethod;
import com.aliakseikul.storenew.entity.enums.StatusTracking;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Tag(name = "DeliveryDto", description = "Class for creating or returning delivery")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {

    @Schema(minLength = 1, maxLength = 44, description = "Delivery address")
    @NotNull(message = "DeliveryAddress name shouldn't be null")
    @Size(min = 1, max = 44, message = "DeliveryAddress should be not null and from 1 to 44 symbols")
    String deliveryAddress;

    @Schema(minLength = 1, maxLength = 44, description = "Payment method")
    @NotNull(message = "PaymentMethod name shouldn't be null")
    PaymentMethod paymentMethod;

    @Schema(minLength = 1, maxLength = 44, description = "Delivery status tracking")
    @NotNull(message = "StatusTracking name shouldn't be null")
    StatusTracking deliveryStatusTracking;
}
