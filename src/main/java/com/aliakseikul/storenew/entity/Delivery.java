package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.StatusTracking;
import com.aliakseikul.storenew.entity.enums.PaymentMethod;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
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
