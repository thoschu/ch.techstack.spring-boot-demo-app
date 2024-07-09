package ch.techstack.demo_rest_app.controller;

import ch.techstack.demo_rest_app.model.User;
import org.springframework.web.bind.annotation.*;

@RestController("/user")
public class UserController {

//    @GetMapping("/register")
//    public String register(@RequestBody User user) {
//        return "UUUUUUUUUUUUU";
//    }

    @GetMapping("/register")
    public String register() {
        return "UUUUUUUUUUUUU";
    }
}
