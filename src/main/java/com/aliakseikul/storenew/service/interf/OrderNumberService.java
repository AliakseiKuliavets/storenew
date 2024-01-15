package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.entity.OrderNumber;

import java.util.List;

public interface OrderNumberService {
    List<OrderNumber> getOrderByUserRecipientId(String id);
}
