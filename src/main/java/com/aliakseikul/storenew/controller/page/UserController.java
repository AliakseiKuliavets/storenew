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

    @PutMapping("/change")
    public ResponseEntity<String> changeUserPropertyById(
            @RequestParam String userId,
            @RequestParam String property,
            @RequestParam String value
    ) {
        String responseMessage;

        switch (property.toLowerCase()) {
            case "name":
                userService.changeUserNameById(userId, value);
                responseMessage = "set new name " + value;
                break;
            case "lastname":
                userService.changeLastNameUserById(userId, value);
                responseMessage = "set new lastname " + value;
                break;
            case "email":
                userService.changeEmailUserById(userId, value);
                responseMessage = "set new email " + value;
                break;
            case "phonenumber":
                userService.changePhoneNumberUserById(userId, value);
                responseMessage = "set new phone number " + value;
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid property: " + property);
        }

        return ResponseEntity.ok("User with ID " + userId + " " + responseMessage);
    }

    //http://localhost:8080/api/user/remove/0480101b-0fa4-4b13-939e-062a7a8c49e6
    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with ID " + userId + " has been deleted");
    }
}
