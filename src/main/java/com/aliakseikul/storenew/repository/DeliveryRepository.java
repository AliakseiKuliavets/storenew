package com.aliakseikul.storenew.repository;

import com.aliakseikul.storenew.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
    @Modifying
    @Query("update Delivery d set d.deliveryAddress = :deliveryAddress where  d.deliveryId = :uuid")
    void changeAddressById(UUID uuid, String deliveryAddress);
}
