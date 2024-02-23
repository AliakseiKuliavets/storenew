package com.aliakseikul.storenew.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A class that will contain error information when an error occurs.")
public record ErrorDto(

        @Schema(description = "Exception name")
        String title,

        @Schema(description = "Error message")
        String message
) {
}
