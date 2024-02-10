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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
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
        return productMapper.productsToProductsDto(productRepository.findByName(name));
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
    public List<ProductDto> searchProductsByCategoryBrandAndName(String category, String brand, String name) {
        if (checkCategory(category)) {
            throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
        }
        if (checkBrand(brand)) {
            throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
        }

        return productMapper.productsToProductsDto(
                productRepository.searchProductsByCategoryBrandAndName(
                        ProductCategory.valueOf(category),
                        ProductBrand.valueOf(brand),
                        name
                ));
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
        Image image = createImage(file1, product);
        if (image != null) {
            Product product1 = productRepository.save(product);
            product1.setPreviewImageId(product1.getImages().get(0).getImageId());
        }

        productMapper.toDto(productRepository.save(product));
    }

    private Image createImage(MultipartFile file1, Product product) {
        Image image = null;
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
        return image;
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
            blob = new SerialBlob(bytes);
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
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateProductNameWithId(String productId, String name) {
        Product product = findById(productId);
        if (product.getProductId() != null) {
            productRepository.updateProductNameWithId(UUID.fromString(productId), name);
        }
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
