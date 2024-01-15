package com.aliakseikul.storenew.service.impl;


import com.aliakseikul.storenew.entity.OrderNumber;
import com.aliakseikul.storenew.repository.OrderNumberRepository;
import com.aliakseikul.storenew.service.interf.OrderNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderNumberServiceImpl implements OrderNumberService {

    private final OrderNumberRepository orderNumberRepository;

    @Override
    public List<OrderNumber> getOrderByUserRecipientId(String id) {
        return orderNumberRepository.getOrderByUserRecipientId(UUID.fromString(id));
    }
}
