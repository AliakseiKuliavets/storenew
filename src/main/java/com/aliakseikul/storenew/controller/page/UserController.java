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

    @GetMapping("/") //http://localhost:8080/api/user/?id=a197d1bb-8990-4b08-ad8a-9ec55718fcb8
    public User getUserById(@RequestParam String id) {
        return userService.findById(id);
    }
}
