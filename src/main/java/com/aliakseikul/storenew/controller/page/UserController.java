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

    @GetMapping("/") //http://localhost:8080/api/user/?id=0480101b-0fa4-4b13-939e-062a7a8c49e6
    public User getUserById(@RequestParam String id) {
        return userService.findById(id);
    }
}
