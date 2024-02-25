package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.Delivery;
import com.aliakseikul.storenew.entity.Payment;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.StatusTracking;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import java.time.LocalDate;

@Tag(name = "OrderNumberDto", description = "For display to end user")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderNumberDto {

    @Schema(minLength = 1, maxLength = 44, description = "Product name")
    String productName;

    @Schema(description = "Day when create that product")
    LocalDate orderNumberDate;

    @Schema(minLength = 1, maxLength = 44, description = "Product status")
    StatusTracking orderStatus;

    @Schema(description = "UUID of delivery")
    Delivery deliveryId;

    @Schema(description = "UUID of payment")
    Payment paymentId;

    @Schema(description = "UUID of recipient")
    User recipientUser;

    @Schema(description = "UUID of sender")
    User senderUser;
}
