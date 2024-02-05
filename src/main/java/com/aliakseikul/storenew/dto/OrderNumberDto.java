package com.aliakseikul.storenew.dto;

import com.aliakseikul.storenew.entity.Delivery;
import com.aliakseikul.storenew.entity.Payment;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.StatusTracking;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderNumberDto {

    String productName;
    LocalDate orderNumberDate;
    StatusTracking orderStatus;
    Delivery deliveryId;
    Payment paymentId;
    User recipientUser;
    User senderUser;
}
