package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.PaymentMethod;
import com.aliakseikul.storenew.entity.enums.PaymentStatusTracking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status_tracking")
    @Enumerated(EnumType.STRING)
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
