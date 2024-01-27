package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public UserDto getUserById(@RequestParam String id) {
        return userService.findById(id);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody UserCreateDto userDto) {
        return userService.addUser(userDto);
    }

    @PutMapping("/change/")
    public ResponseEntity<String> changeUserPropertyById(
            @RequestParam String userId,
            @RequestParam String property,
            @RequestParam String value
    ) {
        return userService.updateProductParamById(userId, property, value);
    }

    @PutMapping("/role/")
    public ResponseEntity<String> changeRole(
            @RequestParam String userId,
            @RequestParam String userRole
    ){
        return userService.changeRole(userId, userRole);
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted");
    }
}
