package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.DeliveryDto;
import com.aliakseikul.storenew.service.interf.DeliveryService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import com.aliakseikul.storenew.validation.interf.Str45LengthCheck;
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
    public DeliveryDto getDeliveryById(@IdChecker @RequestParam String id) {
        return deliveryService.findById(id);
    }

    @PostMapping("/add")
    public DeliveryDto addDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryService.addDelivery(deliveryDto);
    }

    @PutMapping("/change/")
    public ResponseEntity<String> changeAddressById(
            @IdChecker @RequestParam String deliveryId,
            @Str45LengthCheck @RequestParam String deliveryAddress
    ) {
        deliveryService.changeAddressById(deliveryId, deliveryAddress);
        return ResponseEntity.ok("Delivery with ID " + deliveryId + " has been update Address " + deliveryAddress);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteDeliveryById(@IdChecker @RequestParam String deliveryId) {
        deliveryService.deleteDeliveryById(deliveryId);
        return ResponseEntity.ok("Delivery with ID " + deliveryId + " has been delete");
    }
}
