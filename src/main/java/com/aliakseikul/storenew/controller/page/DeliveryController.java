package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.entity.Delivery;
import com.aliakseikul.storenew.service.interf.DeliveryService;
import lombok.RequiredArgsConstructor;
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
}
