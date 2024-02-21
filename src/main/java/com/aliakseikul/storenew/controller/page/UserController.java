package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.service.interf.UserService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import io.swagger.v3.oas.annotations.Operation;
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
            tags = "USER",
            responses = {
                    @ApiResponse(responseCode = "200", description = "find"),
                    @ApiResponse(responseCode = "404", description = "not find")
            }
    )
    public UserDto getUserById(
            @NotNull @IdChecker @RequestParam String id
    ) {
        return userService.findById(id);
    }


    @PostMapping("/add")
    @Operation(summary = "Saves the user",
            description = "Stores UserDto and returns User",
            tags = "USER",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные автора для сохранения",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserCreateDto.class)
                    )
            )
    )
    public User addUser(@RequestBody UserCreateDto userDto) {
        return userService.addUser(userDto);
    }

    @PutMapping("/change/")
    @Operation(summary = "Change the property user",
            description = "Changing the user's fields depending on data entry and fields ",
            tags = "USER"
    )
    public ResponseEntity<String> changeUserPropertyById(
            @NotNull @IdChecker @RequestParam String userId,
            @NotNull @Size(min = 1, max = 44) @RequestParam String property,
            @NotNull @Size(min = 1, max = 44) @RequestParam String value
    ) {
        return userService.updateProductParamById(userId, property, value);
    }

    @PutMapping("/role/")
    public ResponseEntity<String> changeRole(
            @NotNull @IdChecker @RequestParam String userId,
            @NotNull @Size(min = 1, max = 44) @RequestParam String userRole
    ) {
        return userService.changeRole(userId, userRole);
    }

    @DeleteMapping("/remove/{userId}")
    @Operation(summary = "basic user rest delete method by provided id",
            description = "deletes a user from database by given id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "user successfully deleted")
            })
    public ResponseEntity<String> deleteUserById(
            @NotNull @IdChecker @PathVariable("userId") String userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted");
    }
}
