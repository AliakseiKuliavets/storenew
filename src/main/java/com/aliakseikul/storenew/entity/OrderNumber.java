package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.StatusTracking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "order_number_id")
    private UUID orderNumberId;

    @Column(name = "order_number_date")
    private LocalDate orderNumberDate;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product productId;

    @OneToOne
    @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id")
    private Delivery deliveryId;

    @Column(name = "order_status")
    private StatusTracking orderStatus;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private Payment paymentId;

    @ManyToOne
    @JoinColumn(name = "recipient_user_id", referencedColumnName = "user_id")
    private User recipientUserId;

    @ManyToOne
    @JoinColumn(name = "sender_user_id", referencedColumnName = "user_id")
    private User senderUserId;

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
                ", recipientUserId=" + recipientUserId +
                ", senderUserId=" + senderUserId +
                '}';
    }
}
