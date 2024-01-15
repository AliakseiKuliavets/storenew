package com.aliakseikul.storenew.controller.page;


import com.aliakseikul.storenew.entity.OrderNumber;
import com.aliakseikul.storenew.service.interf.OrderNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderNumberController {

    private final OrderNumberService orderNumberService;

    @GetMapping("/idRecipient/")//http://localhost:8080/api/order/idRecipient/?id=a197d1bb-8990-4b08-ad8a-9ec55718fcb8
    public List<OrderNumber> getOrderByUserRecipientId(@RequestParam String id) {
        return orderNumberService.getOrderByUserRecipientId(id);
    }
}
