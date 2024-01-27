package com.aliakseikul.storenew.service.impl;


import com.aliakseikul.storenew.dto.OrderNumberDto;
import com.aliakseikul.storenew.exception.exeptions.OrderNotFoundExceptions;
import com.aliakseikul.storenew.exception.message.ErrorMessage;
import com.aliakseikul.storenew.mapper.OrderNumberMapper;
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

    private final OrderNumberMapper orderNumberMapper;

    @Override
    public OrderNumberDto getOrderById(String id) {
        return orderNumberMapper
                .toDto(orderNumberRepository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new OrderNotFoundExceptions(ErrorMessage.ORDER_NUMBER_NOT_FOUND)));
    }

    @Override
    public List<OrderNumberDto> getOrderByUserRecipientId(String id) {
        getOrderById(id);
        return orderNumberMapper.orderNumbersToOrderNumbersDto(
                orderNumberRepository.getOrderByUserRecipientId(UUID.fromString(id)));
    }
}
