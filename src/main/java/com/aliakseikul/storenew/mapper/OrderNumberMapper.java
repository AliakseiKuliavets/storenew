package com.aliakseikul.storenew.mapper;

import com.aliakseikul.storenew.dto.OrderNumberDto;
import com.aliakseikul.storenew.entity.OrderNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderNumberMapper {

    @Mapping(source = "productId.productName", target = "productName")
    OrderNumberDto toDto(OrderNumber orderNumber);

    List<OrderNumberDto> orderNumbersToOrderNumbersDto(List<OrderNumber> orderNumber);
}
