package com.aliakseikul.storenew.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "ErrorDto", description = "A class that will contain error information when an error occurs.")
public record ErrorDto(

        @Schema(description = "Exception name")
        String title,

        @Schema(description = "Error message")
        String message
) {
}
