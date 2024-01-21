
package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.StatusTracking;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_number")
public class OrderNumber {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "order_number_id")
    private UUID orderNumberId;

    @Column(name = "order_number_date")
    private LocalDate orderNumberDate;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private StatusTracking orderStatus;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product productId;

    @OneToOne
    @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id")
    private Delivery deliveryId;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private Payment paymentId;

    @ManyToOne
    @JsonBackReference("recipientOrderNumbersReference")
    @JoinColumn(name = "recipient_user_id", referencedColumnName = "user_id")
    private User recipientUser;

    @ManyToOne
    @JsonBackReference("senderOrderNumbersReference")
    @JoinColumn(name = "sender_user_id", referencedColumnName = "user_id")
    private User senderUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderNumber that = (OrderNumber) o;
        return Objects.equals(orderNumberId, that.orderNumberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumberId);
    }

    @Override
    public String toString() {
        return "OrderNumber{" +
                "orderNumberId=" + orderNumberId +
                ", orderNumberDate=" + orderNumberDate +
                ", productId=" + productId +
                ", deliveryId=" + deliveryId +
                ", orderStatus=" + orderStatus +
                ", paymentId=" + paymentId +
                ", recipientUserId=" + recipientUser +
                ", senderUserId=" + senderUser +
                '}';
    }
}
