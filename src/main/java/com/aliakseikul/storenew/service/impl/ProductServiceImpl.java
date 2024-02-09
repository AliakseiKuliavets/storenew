package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.dto.ProductDto;
import com.aliakseikul.storenew.entity.Image;
import com.aliakseikul.storenew.entity.Product;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.aliakseikul.storenew.exception.exeptions.*;
import com.aliakseikul.storenew.exception.message.ErrorMessage;
import com.aliakseikul.storenew.mapper.ProductMapper;
import com.aliakseikul.storenew.repository.ProductRepository;
import com.aliakseikul.storenew.repository.UserRepository;
import com.aliakseikul.storenew.service.interf.ProductService;
import com.aliakseikul.storenew.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final ProductMapper productMapper;

    @Override
    public Product findById(String id) {
        return productRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<ProductDto> findByName(String name) {
        if (name != null) {
            return productMapper.productsToProductsDto(productRepository.findByName(name));
        }
        return productMapper.productsToProductsDto(productRepository.findAll());
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.productsToProductsDto(productRepository.findAll());
    }

    @Override
    public List<ProductDto> getAllProductsByCategory(String category) {
        String categoryUp = category.toUpperCase();
        if (checkCategory(categoryUp)) {
            throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.getAllProductsByCategory(ProductCategory.valueOf(categoryUp)));
    }

    @Override
    public List<ProductDto> getAllProductsByBrand(String brand) {
        String brandUp = brand.toUpperCase();
        if (checkBrand(brandUp)) {
            throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.getAllProductsByBrand(ProductBrand.valueOf(brandUp)));
    }

    @Override
    public List<ProductDto> searchProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        double minPriceS = minPrice.doubleValue();
        double maxPriceS = maxPrice.doubleValue();
        if (minPriceS < 0 || maxPriceS < 0) {
            throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
        }
        if (minPriceS > maxPriceS) {
            throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
        }
        return productMapper.productsToProductsDto(productRepository.findByPriceBetween(minPriceS, maxPriceS));
    }

    @Override
    public List<ProductDto> searchProductsByCategoryBrand(String category, String brand) {
        if (checkCategory(category)) {
            throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
        }
        if (checkBrand(brand)) {
            throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.findByCategoryBrand(ProductCategory.valueOf(category), ProductBrand.valueOf(brand)));
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        User placedByUser = userRepository.findUserByNickName(productDto.getUserNickname())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        Product product = Product.builder()
                .productName(productDto.getProductName())
                .productPrice(productDto.getProductPrice())
                .productDescription(productDto.getProductDescription())
                .productCategory(productDto.getProductCategory())
                .productBrand(productDto.getProductBrand())
                .placedByUser(placedByUser)
                .build();
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public void createProduct(Principal principal, ProductDto productDto, MultipartFile file1) {
        User placedByUser = userRepository.findUserByNickName(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        Product product = Product.builder()
                .productName(productDto.getProductName())
                .productPrice(productDto.getProductPrice())
                .productDescription(productDto.getProductDescription())
                .productCategory(productDto.getProductCategory())
                .productBrand(productDto.getProductBrand())
                .placedByUser(placedByUser)
                .dateOfCreate(new Date(System.currentTimeMillis()))
                .build();

        product.setPlacedByUser(userService.getUserByPrincipal(principal));
        createImage(file1, product);
        Product product1 = productRepository.save(product);
        product1.setPreviewImageId(product1.getImages().get(0).getImageId());

        productMapper.toDto(productRepository.save(product));
    }

    private void createImage(MultipartFile file1, Product product) {
        Image image;
        if (file1.getSize() != 0) {
            image = toImage(file1);
            image.setImageIsPreviewImage(true);
            image.setProduct(product);
            List<Image> list = product.getImages();
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(image);
            product.setImages(list);
        }
    }

    private Image toImage(MultipartFile file) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Blob blob;
        try {
            blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image();
        image.setImageName(file.getName());
        image.setImageOriginalFileName(file.getOriginalFilename());
        image.setImageContentType(file.getContentType());
        image.setImageSize(blob);
        try {
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateProductParamById(String productId, String tableName, String value) {
        findById(productId);
        UUID productUuid = UUID.fromString(productId);

        String responseMessage;
        switch (tableName.toLowerCase()) {
            case "name":
                productRepository.updateProductName(productUuid, value);
                responseMessage = "set new name " + value;
                break;
            case "price":
                if (checkNumber(value)) {
                    throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
                }
                double number = Double.parseDouble(value);
                if (number < 0) {
                    throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
                }
                productRepository.updateProductPrice(productUuid, value);
                responseMessage = "set new price " + value;
                break;
            case "descriptions":
                productRepository.updateProductDescriptions(productUuid, value);
                responseMessage = "set new descriptions " + value;
                break;
            case "category":
                if (checkCategory(value)) {
                    throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
                } else {
                    productRepository.updateProductCategory(productUuid, ProductCategory.valueOf(value));
                    responseMessage = "set new category " + value;
                    break;
                }
            case "brand":
                if (checkBrand(value)) {
                    throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
                } else {
                    productRepository.updateProductBrand(productUuid, ProductBrand.valueOf(value));
                    responseMessage = "set new phone brand " + value;
                    break;
                }
            default:
                return ResponseEntity.badRequest().body("Invalid property: " + tableName);
        }
        return ResponseEntity.ok("Product with ID " + productId + " " + responseMessage);
    }

    private boolean checkNumber(String value) {
        String template =
                "^[0-9]+$\n";
        return value.matches(template);
    }

    @Override
    public void deleteById(String productId) {
        System.out.println("NEN");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(productId);
        findById(productId);
        productRepository.deleteById(UUID.fromString(productId));
    }

    private boolean checkCategory(String category) {
        return !(ProductCategory.getProductCategoryList().contains(category));
    }

    private boolean checkBrand(String brand) {
        return !(ProductBrand.getProductBrandList().contains(brand));
    }
}
