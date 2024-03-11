package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.DeliveryDto;
import com.aliakseikul.storenew.dto.ErrorDto;
import com.aliakseikul.storenew.service.interf.DeliveryService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "DeliveryController", description = "rest class for processing requests for delivery")
@SecurityRequirement(name = "bearerAuth")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Finds the DeliveryDto",
            description = "Finds the delivery, converts it to DeliveryDto and returns",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = DeliveryDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Delivery not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/")
    public DeliveryDto getDeliveryById(
            @Parameter(
                    description = "ID of delivery to be retrieved",
                    required = true)
            @NotNull @IdChecker @RequestParam String id
    ) {
        return deliveryService.findById(id);
    }

    @Operation(summary = "Saves the delivery",
            description = "Stores DeliveryDto and returns DeliveryDto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DeliveryDto data for saving",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DeliveryDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = DeliveryDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Delivery not create, because its null",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @PostMapping("/add")
    public DeliveryDto addDelivery(
            @RequestBody @Valid DeliveryDto deliveryDto
    ) {
        return deliveryService.addDelivery(deliveryDto);
    }

    @Operation(summary = "Change the delivery address",
            description = "change delivery address by id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(
                                    schema = @Schema(defaultValue = "Delivery with ID \" + deliveryId + \" " +
                                            "has been update Address \" + deliveryAddress"),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Delivery not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @PutMapping("/change/")
    public ResponseEntity<String> changeAddressById(
            @Parameter(
                    description = "ID of delivery to be retrieved",
                    required = true)
            @NotNull @IdChecker @RequestParam String deliveryId,
            @Schema(minLength = 1, maxLength = 44,
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    description = "Delivery address")
            @NotNull @Size(min = 1, max = 44) @RequestParam String deliveryAddress
    ) {
        deliveryService.changeAddressById(deliveryId, deliveryAddress);
        return ResponseEntity.ok("Delivery with ID " + deliveryId + " has been update Address " + deliveryAddress);
    }

    @Operation(summary = "Delete delivery",
            description = "deletes a delivery from database by given id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content
                                    (schema = @Schema(defaultValue = "Delivery with ID \" + deliveryId + " +
                                            "\" has been delete\""))}),
                    @ApiResponse(responseCode = "404",
                            description = "Delivery not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteDeliveryById(
            @Parameter(
                    description = "ID of delivery to be retrieved",
                    required = true)
            @NotNull @IdChecker @RequestParam String deliveryId
    ) {
        deliveryService.deleteDeliveryById(deliveryId);
        return ResponseEntity.ok("Delivery with ID " + deliveryId + " has been delete");
    }
}
