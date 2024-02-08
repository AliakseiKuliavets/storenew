package com.aliakseikul.storenew.entity;

import com.aliakseikul.storenew.entity.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "user_nick_name")
    private String userNickname;

    @Column(name = "user_password")
    private String userPassword;

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

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "placedByUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("placedUserReference")
    private List<Product> productsPlaced;

    @OneToMany(mappedBy = "purchasedByUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("purchasedUserReference")
    private List<Product> productsPurchased;

    @OneToMany(mappedBy = "recipientUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("recipientOrderNumbersReference")
    private List<OrderNumber> recipientOrderNumbers;

    @OneToMany(mappedBy = "senderUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("senderOrderNumbersReference")
    private List<OrderNumber> senderOrderNumber;

    @OneToMany(mappedBy = "userReviewed", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("userReviewReference")
    private List<Review> userReviewed;

    @OneToMany(mappedBy = "userReceivedReview", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("userReceivedReviewReference")
    private List<Review> userReceivedReview;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userNickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

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
                ", userNickname='" + userNickname + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}