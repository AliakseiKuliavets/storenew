package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.service.interf.UserService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import com.aliakseikul.storenew.validation.interf.Str45LengthCheck;
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
    public UserDto getUserById(@IdChecker @RequestParam String id) {
        return userService.findById(id);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody UserCreateDto userDto) {
        return userService.addUser(userDto);
    }

    @PutMapping("/change/")
    public ResponseEntity<String> changeUserPropertyById(
            @IdChecker @RequestParam String userId,
            @Str45LengthCheck @RequestParam String property,
            @Str45LengthCheck @RequestParam String value
    ) {
        return userService.updateProductParamById(userId, property, value);
    }

    @PutMapping("/role/")
    public ResponseEntity<String> changeRole(
            @IdChecker @RequestParam String userId,
            @RequestParam String userRole
    ) {
        return userService.changeRole(userId, userRole);
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> deleteUserById(@IdChecker @PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted");
    }
}
