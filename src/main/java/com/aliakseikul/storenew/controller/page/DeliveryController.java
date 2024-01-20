package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.entity.Delivery;
import com.aliakseikul.storenew.service.interf.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/")
    public Delivery getDeliveryById(@RequestParam String id) {
        return deliveryService.findById(id);
    }
    // http://localhost:8080/api/delivery/delivery/?id=1fac8484-5093-4532-b6d8-d632257c84cc

    @PostMapping("/add") // http://localhost:8080/api/delivery/add
    public Delivery addDelivery(@RequestBody Delivery delivery) {
        return deliveryService.addDelivery(delivery);
    }

    @PutMapping("/change/")
    public ResponseEntity<String> changeAddressById(
            @RequestParam String deliveryId,
            @RequestParam String deliveryAddress
    ) {
        deliveryService.changeAddressById(deliveryId, deliveryAddress);
        return ResponseEntity.ok("Delivery with ID " + deliveryId + " has been update Address " + deliveryAddress);
    }
    // api/delivery/change/?deliveryId=1fac8484-5093-4532-b6d8-d632257c84cc&deliveryAddress=Poland, Warsaw

    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteDeliveryById(@RequestParam String deliveryId) {
        deliveryService.deleteDeliveryById(deliveryId);
        return ResponseEntity.ok("Delivery with ID " + deliveryId + " has been delete");
    }
    // http://localhost:8080/api/delivery/delete/?id=1fac8484-5093-4532-b6d8-d632257c84cc
}
