package com.aliakseikul.storenew.controller.page;


import com.aliakseikul.storenew.dto.ErrorDto;
import com.aliakseikul.storenew.dto.OrderNumberDto;
import com.aliakseikul.storenew.service.interf.OrderNumberService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "OrderNumberController", description = "a class that executes queries related to the order number")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderNumberController {

    private final OrderNumberService orderNumberService;

    @Operation(summary = "Finds the order number",
            description = "Finds the order number by Id and Return OrderNumberDto",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = OrderNumberDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403",
                            description = "Not Autowired",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Order Number not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/")
    public OrderNumberDto getOrderNumberById(
            @Parameter(
                    description = "ID of order number to be retrieved",
                    required = true)
            @NotNull @IdChecker @RequestParam String id
    ) {
        return orderNumberService.getOrderById(id);
    }

    @Operation(summary = "Finds the order number",
            description = "Finds the order number by Id of Recipient and Return OrderNumberDto",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = OrderNumberDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "403",
                            description = "Not Autowired",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/idRecipient/")
    public List<OrderNumberDto> getOrderByUserRecipientId(
            @Parameter(
                    description = "ID of recipient to be retrieved",
                    required = true)
            @NotNull @IdChecker @RequestParam String id) {
        return orderNumberService.getOrderByUserRecipientId(id);
    }
}
