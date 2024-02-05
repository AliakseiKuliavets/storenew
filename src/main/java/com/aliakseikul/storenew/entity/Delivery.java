package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.PaymentMethod;
import com.aliakseikul.storenew.entity.enums.StatusTracking;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery")
public class Delivery {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "delivery_status_tracking")
    @Enumerated(EnumType.STRING)
    private StatusTracking deliveryStatusTracking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(deliveryId, delivery.deliveryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "deliveryId=" + deliveryId +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", deliveryStatusTracking=" + deliveryStatusTracking +
                '}';
    }
}
