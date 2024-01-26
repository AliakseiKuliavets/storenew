package com.aliakseikul.storenew.mapper;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "placedByUser.userNickname", target = "userNickname")
    ProductDto toDto(Product byId);

    List<ProductDto> productsToProductsDto(List<Product> cards);
}
