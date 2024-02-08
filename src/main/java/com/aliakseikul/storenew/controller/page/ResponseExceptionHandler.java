package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.ErrorDto;
import com.aliakseikul.storenew.exception.exeptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(value = BrandNotFoundExceptions.class)
    public ResponseEntity<ErrorDto> brandNotFoundExceptions(BrandNotFoundExceptions exception) {
        ErrorDto errorDto = new ErrorDto(
                "Brand Not Found",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = CategoryNotFoundExceptions.class)
    public ResponseEntity<ErrorDto> categoryNotFoundExceptions(CategoryNotFoundExceptions exception) {
        ErrorDto errorDto = new ErrorDto(
                "Category Not Found",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = DeliveryNotFoundException.class)
    public ResponseEntity<ErrorDto> deliveryNotFoundException(DeliveryNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                "Delivery Not Found",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = OrderNotFoundExceptions.class)
    public ResponseEntity<ErrorDto> orderNotFoundExceptions(OrderNotFoundExceptions exception) {
        ErrorDto errorDto = new ErrorDto(
                "Order Not Found",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> productNotFoundException(ProductNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                "Product Not Found",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorDto> userNotFoundException(UserNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                "User Not Found",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = UserRoleNotFoundException.class)
    public ResponseEntity<ErrorDto> userRoleNotFoundException(UserRoleNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                "User role Not Found",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDto> exceptionHandle(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Something Wrong", exception.getMessage()));
    }
}
