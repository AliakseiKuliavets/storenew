package com.aliakseikul.storenew.repository;

import com.aliakseikul.storenew.entity.OrderNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderNumberRepository extends JpaRepository<OrderNumber, UUID> {

    @Query("select o from OrderNumber o where o.recipientUser.userId = :userId")
    List<OrderNumber> getOrderByUserRecipientId(UUID userId);
}
