package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.StatusTracking;
import com.aliakseikul.storenew.entity.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery")
public class Delivery {

    @Id
    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "delivery_status_tracking")
    private StatusTracking deliveryStatusTracking;
}
