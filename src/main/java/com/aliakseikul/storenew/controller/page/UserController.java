package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.ErrorDto;
import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.service.interf.UserService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/")
    @Operation(summary = "Finds the User",
            description = "Finds the user, converts it to UserDto and returns",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = UserDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Employee not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    public UserDto getUserById(
            @Parameter(
                    description = "ID of employee to be retrieved",
                    required = true)
            @NotNull @IdChecker @RequestParam String id
    ) {
        return userService.findById(id);
    }


    @PostMapping("/add")
    @Operation(summary = "Saves the user",
            description = "Stores UserDto and returns User",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Author data for saving",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserCreateDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(implementation = User.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Employee not create, because NickName is already taken",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    public User addUser(@RequestBody UserCreateDto userDto) {
        return userService.addUser(userDto);
    }

    @PutMapping("/change/")
    @Operation(summary = "Change the property user",
            description = "Changing the user's fields depending on data entry and fields ",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Employee not found, or wrong property or value",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    public ResponseEntity<String> changeUserPropertyById(
            @Parameter(
                    description = "ID of employee to be retrieved",
                    required = true)
            @NotNull @IdChecker @RequestParam String userId,
            @Parameter(
                    description = "Which field will be changed choose between {name,last name,email,phone number}",
                    required = true)
            @NotNull @Size(min = 1, max = 44) @RequestParam String property,
            @Parameter(
                    description = "The value that will need to be changed to",
                    required = true)
            @NotNull @Size(min = 1, max = 44) @RequestParam String value
    ) {
        return userService.updateProductParamById(userId, property, value);
    }

    @PutMapping("/change/role")
    @Operation(summary = "Change the role user",
            description = "Changing the user's role",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content(schema = @Schema(),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Employee not found or role is not correct",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            }
    )
    public ResponseEntity<String> changeUserRoleById(
            @Parameter(
                    description = "ID of employee to be retrieved",
                    required = true)
            @NotNull @IdChecker @RequestParam String userId,
            @Parameter(
                    description = "User role for which changes will be made",
                    required = true)
            @NotNull @Size(min = 1, max = 44) @RequestParam String role
    ) {
        return userService.changeRole(userId, role);
    }

    @DeleteMapping("/remove/{userId}")
    @Operation(summary = "basic user rest delete method by provided id",
            description = "deletes a user from database by given id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great",
                            content = {@Content
                                    (schema = @Schema(defaultValue = "User with ID \" + userId + \" has been deleted"),
                                            mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404",
                            description = "Employee not found",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "Something wrong",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    public ResponseEntity<String> deleteUserById(
            @Parameter(
                    description = "ID of employee to be retrieved",
                    required = true)
            @NotNull @IdChecker @PathVariable("userId") String userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted");
    }
}
