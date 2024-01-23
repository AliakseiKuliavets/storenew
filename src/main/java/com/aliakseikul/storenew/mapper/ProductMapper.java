package com.aliakseikul.storenew.mapper;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product byId);

    List<ProductDto> productsToProductsDto(List<Product> cards);

}
