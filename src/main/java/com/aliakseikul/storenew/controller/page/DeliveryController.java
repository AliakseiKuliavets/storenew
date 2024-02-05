package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.DeliveryDto;
import com.aliakseikul.storenew.service.interf.DeliveryService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/")
    public DeliveryDto getDeliveryById(
            @NotNull @IdChecker @RequestParam String id
    ) {
        return deliveryService.findById(id);
    }

    @PostMapping("/add")
    public DeliveryDto addDelivery(
            @RequestBody DeliveryDto deliveryDto
    ) {
        return deliveryService.addDelivery(deliveryDto);
    }

    @PutMapping("/change/")
    public ResponseEntity<String> changeAddressById(
            @NotNull @IdChecker @RequestParam String deliveryId,
            @NotNull @Size(min = 1, max = 44) @RequestParam String deliveryAddress
    ) {
        deliveryService.changeAddressById(deliveryId, deliveryAddress);
        return ResponseEntity.ok("Delivery with ID " + deliveryId + " has been update Address " + deliveryAddress);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteDeliveryById(
            @NotNull @IdChecker @RequestParam String deliveryId
    ) {
        deliveryService.deleteDeliveryById(deliveryId);
        return ResponseEntity.ok("Delivery with ID " + deliveryId + " has been delete");
    }
}
