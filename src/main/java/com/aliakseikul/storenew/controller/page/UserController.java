package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.dto.auth.AuthenticationRequest;
import com.aliakseikul.storenew.dto.auth.RegisterRequest;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.service.interf.UserService;
import com.aliakseikul.storenew.validation.interf.IdChecker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register.html");
        return modelAndView;
    }

    @PostMapping("/register")
    public void register(
            RegisterRequest request
    ) {
        userService.register(request);
    }

    @GetMapping("/authentication")
    public ModelAndView authentication() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @PostMapping("/authentication")
    public void authentication(AuthenticationRequest request) {
        System.out.println("-----------------");
        userService.authenticate(request);
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
