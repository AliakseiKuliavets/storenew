package com.aliakseikul.storenew.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "reviewed")
public class Review {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "reviewed_id")
    private UUID reviewedId;

    @ManyToOne
    @JsonBackReference("userReviewReference")
    @JoinColumn(name = "user_reviewed", referencedColumnName = "user_id")
    private User userReviewed;

    @ManyToOne
    @JsonBackReference("userReceivedReviewReference")
    @JoinColumn(name = "user_received_review", referencedColumnName = "user_id")
    private User userReceivedReview;

    @Column(name = "review_rating")
    private Double reviewRating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewedId, review.reviewedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewedId);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewedId=" + reviewedId +
                ", userReviewed=" + userReviewed +
                ", userReceivedReview=" + userReceivedReview +
                ", reviewRating=" + reviewRating +
                '}';
    }
}
