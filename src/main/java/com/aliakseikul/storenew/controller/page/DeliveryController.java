package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.DeliveryDto;
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
    public DeliveryDto getDeliveryById(@RequestParam String id) {
        return deliveryService.findById(id);
    }

    @PostMapping("/add") // http://localhost:8080/api/delivery/add
    public DeliveryDto addDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryService.addDelivery(deliveryDto);
    }

    @PutMapping("/change/")
    public ResponseEntity<String> changeAddressById(
            @RequestParam String deliveryId,
            @RequestParam String deliveryAddress
    ) {
        deliveryService.changeAddressById(deliveryId, deliveryAddress);
        return ResponseEntity.ok("Delivery with ID " + deliveryId + " has been update Address " + deliveryAddress);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteDeliveryById(@RequestParam String deliveryId) {
        deliveryService.deleteDeliveryById(deliveryId);
        return ResponseEntity.ok("Delivery with ID " + deliveryId + " has been delete");
    }
}
