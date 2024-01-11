package com.aliakseikul.storenew.entity;

import jakarta.persistence.*;
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
@Table(name = "reviewed")
public class Review {

    @Id
    @Column(name = "reviewed_id")
    private UUID reviewedId;

    @ManyToOne
    @JoinColumn(name = "user_reviewed", referencedColumnName = "user_id")
    private User userReviewed;

    @ManyToOne
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
