package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.ErrorDto;
import com.aliakseikul.storenew.entity.Image;
import com.aliakseikul.storenew.service.interf.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@Tag(name = "ImageController", description = "finds pictures and displays them")
@RestController
@RequiredArgsConstructor
@RequestMapping()
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Finds the Image",
            description = "Finds the image, by id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(description = "Return media file"),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Image not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    @GetMapping("/images/{id}")
    public ResponseEntity<?> getImageById(
            @Parameter(
                    description = "ID of image to be retrieved",
                    required = true)
            @PathVariable String id
    ) {
        String idN = id.replaceAll("[^0-9]", "");
        Image image = imageService.findById(Long.valueOf(idN));
        byte[] imageBytes;
        try {
            imageBytes = image.getImageSize().getBytes(1, (int) (image.getImageSize().length()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }
}
