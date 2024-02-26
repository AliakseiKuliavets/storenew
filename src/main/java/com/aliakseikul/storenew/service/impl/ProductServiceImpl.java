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
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final ProductMapper productMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Product findById(String id) {
        return productRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<ProductDto> findByName(String name) {
        return productMapper.productsToProductsDto(productRepository.findByName(name));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.productsToProductsDto(productRepository.findAll());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<ProductDto> getAllProductsByCategory(String category) {
        String categoryUp = category.toUpperCase();
        if (checkCategory(categoryUp)) {
            throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.getAllProductsByCategory(ProductCategory.valueOf(categoryUp)));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<ProductDto> getAllProductsByBrand(String brand) {
        String brandUp = brand.toUpperCase();
        if (checkBrand(brandUp)) {
            throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
        }
        return productMapper.productsToProductsDto(
                productRepository.getAllProductsByBrand(ProductBrand.valueOf(brandUp)));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
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


    @Transactional(isolation = Isolation.READ_COMMITTED)
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

    @Transactional(isolation = Isolation.REPEATABLE_READ)
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
                .dateOfCreate(new Date(System.currentTimeMillis()))
                .build();
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
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
            try {
                image = toImage(file1);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
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

    private Image toImage(MultipartFile file) throws IOException, SQLException {
        byte[] bytes;
        bytes = file.getBytes();
        Blob blob;
        blob = new SerialBlob(bytes);
        Image image = new Image();
        image.setImageName(file.getName());
        image.setImageOriginalFileName(file.getOriginalFilename());
        image.setImageContentType(file.getContentType());
        image.setImageSize(blob);
        image.setBytes(file.getBytes());
        return image;
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void updateProductNameWithId(String productId, String name) {
        Product product = findById(productId);
        if (product.getProductId() != null) {
            productRepository.updateProductNameWithId(UUID.fromString(productId), name);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void updateProductDescriptionWithId(String productId, String description) {
        Product product = findById(productId);
        if (product.getProductId() != null) {
            productRepository.updateProductDescriptionWithId(UUID.fromString(productId), description);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void updateProductPriceWithId(String productId, String price) {
        Product product = findById(productId);
        if (product.getProductId() != null) {
            long longPrice = Long.parseLong(price);
            if (longPrice > 1_000_000 || longPrice < 0) {
                throw new NumberExceptions(ErrorMessage.NUMBER_ERROR);
            }
            BigDecimal newPrice = BigDecimal.valueOf(longPrice);
            productRepository.updateProductPriceWithId(UUID.fromString(productId), newPrice);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void updateProductCategoryWithId(String productId, String category) {
        Product product = findById(productId);
        if (product.getProductId() != null) {
            if (checkCategory(category)) {
                throw new CategoryNotFoundExceptions(ErrorMessage.CATEGORY_NOT_FOUND);
            }
            productRepository.updateProductCategoryWithId(
                    UUID.fromString(productId), ProductCategory.valueOf(category));
        }
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void updateProductBrandWithId(String productId, String brand) {
        Product product = findById(productId);
        if (product.getProductId() != null) {
            if (checkBrand(brand)) {
                throw new BrandNotFoundExceptions(ErrorMessage.BRAND_NOT_FOUND);
            }
            productRepository.updateProductBrandWithId(UUID.fromString(productId), ProductBrand.valueOf(brand));
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteById(String productId) {
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
