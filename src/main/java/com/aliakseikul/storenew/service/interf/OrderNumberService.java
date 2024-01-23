package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.dto.OrderNumberDto;

import java.util.List;

public interface OrderNumberService {

    OrderNumberDto getOrderById(String id);

    List<OrderNumberDto> getOrderByUserRecipientId(String id);

}
