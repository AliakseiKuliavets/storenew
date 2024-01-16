package com.aliakseikul.storenew.controller.page;

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

    @GetMapping("/") //http://localhost:8080/api/user/?id=a197d1bb-8990-4b08-ad8a-9ec55718fcb8
    public User getUserById(@RequestParam String id) {
        return userService.findById(id);
    }

    @PostMapping("/add") //http://localhost:8080/api/user/add
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
    /*
    {
     "userFirstName": "Alexander",
    "userLastName": "Karadiaur",
    "userEmail": "butylka@gmail.com",
    "userPhoneNumber": "+497576152478",
    "userVerifiedAccount": true
    }
     */

    //http://localhost:8080/api/user/remove/0480101b-0fa4-4b13-939e-062a7a8c49e6
    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted");
    }
}
