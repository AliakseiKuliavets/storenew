package com.aliakseikul.storenew.controller.page;


import com.aliakseikul.storenew.dto.OrderNumberDto;
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

    @GetMapping("/")
    public OrderNumberDto getOrderNumberById(@RequestParam String id) {
        return orderNumberService.getOrderById(id);
    }

    @GetMapping("/idRecipient/")
    public List<OrderNumberDto> getOrderByUserRecipientId(@RequestParam String id) {
        return orderNumberService.getOrderByUserRecipientId(id);
    }
}
