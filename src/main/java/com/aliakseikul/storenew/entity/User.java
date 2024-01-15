package com.aliakseikul.storenew.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "user_first_name")
    private String userFirstName;

    @Column(name = "user_last_name")
    private String userLastName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_phone_number")
    private String userPhoneNumber;

    @Column(name = "user_verified_account")
    private boolean userVerifiedAccount;

    @OneToMany(mappedBy = "placedByUser")
    private List<Product> productsPlaced;

    @OneToMany(mappedBy = "purchasedByUser")
    private List<Product> productsPurchased;

    @OneToMany(mappedBy = "recipientUser")
    private List<OrderNumber> recipientOrderNumbers;

    @OneToMany(mappedBy = "senderUser")
    private List<OrderNumber> senderOrderNumber;

    @OneToMany(mappedBy = "userReviewed")
    private List<Review> userReviewed;

    @OneToMany(mappedBy = "userReceivedReview")
    private List<Review> userReceivedReview;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userVerifiedAccount=" + userVerifiedAccount +
                ", productsPlaced=" + productsPlaced +
                ", productsPurchased=" + productsPurchased +
                ", recipientOrderNumbers=" + recipientOrderNumbers +
                ", senderOrderNumber=" + senderOrderNumber +
                ", userReviewed=" + userReviewed +
                ", userReceivedReview=" + userReceivedReview +
                '}';
    }
}
