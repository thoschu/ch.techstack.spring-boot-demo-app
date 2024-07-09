package ch.techstack.demo_rest_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.techstack.demo_rest_app.model.User;
import ch.techstack.demo_rest_app.service.UserService;

@RestController
public class UserController {
    private final static String text = "Foo bar baz user";
    private UserService userService;

    public UserController(UserService userService) {
        super();

        this.userService = userService;
    }

    @GetMapping("/register")
    public String registered() {
        return UserController.text;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody() User user) {
        try {
            User newUser = this.userService.save(user);

            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Error err) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
    }
}
