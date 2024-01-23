package com.aliakseikul.storenew.mapper;

import com.aliakseikul.storenew.dto.DeliveryDto;
import com.aliakseikul.storenew.entity.Delivery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    DeliveryDto toDto(Delivery byId);
}
