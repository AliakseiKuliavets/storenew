package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.PaymentMethod;
import com.aliakseikul.storenew.entity.enums.PaymentStatusTracking;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "payment")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status_tracking")
    private PaymentStatusTracking paymentStatusTracking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatusTracking=" + paymentStatusTracking +
                '}';
    }
}
