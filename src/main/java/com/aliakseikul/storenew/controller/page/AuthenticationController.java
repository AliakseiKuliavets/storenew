package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.ErrorDto;
import com.aliakseikul.storenew.dto.auth.AuthenticationRequest;
import com.aliakseikul.storenew.dto.auth.AuthenticationResponse;
import com.aliakseikul.storenew.service.impl.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AuthenticationController", description = "serves for user authentication")
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(summary = "Authenticate User",
            description = "Authenticate User, and return token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Authenticate User, and return token",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Return token",
                            content = {@Content(schema = @Schema(implementation = AuthenticationResponse.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}