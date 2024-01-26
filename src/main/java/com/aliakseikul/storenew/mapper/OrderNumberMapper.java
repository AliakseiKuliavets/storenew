package com.aliakseikul.storenew.mapper;

import com.aliakseikul.storenew.dto.OrderNumberDto;
import com.aliakseikul.storenew.entity.OrderNumber;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderNumberMapper {
    OrderNumberDto toDto(OrderNumber orderNumber);

    List<OrderNumberDto> orderNumbersToOrderNumbersDto(List<OrderNumber> orderNumber);
}
