package com.aliakseikul.storenew.controller.page;

import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public User getUserById(@RequestParam String id) {
        return userService.findById(id);
    }
}
