package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.PaymentOptions;
import com.aliakseikul.storenew.entity.enums.PaymentStatusTracking;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "payment_options")
    private PaymentOptions paymentOptions;

    @Column(name = "payment_status_tracking")
    private PaymentStatusTracking paymentStatusTracking;
}
