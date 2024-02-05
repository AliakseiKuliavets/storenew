package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.service.interf.UserService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
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
    public UserDto getUserById(
            @NotNull @IdChecker @RequestParam String id
    ) {
        return userService.findById(id);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody UserCreateDto userDto) {
        return userService.addUser(userDto);
    }

    @PutMapping("/change/")
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
    public ResponseEntity<String> deleteUserById(
            @NotNull @IdChecker @PathVariable("userId") String userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted");
    }
}
