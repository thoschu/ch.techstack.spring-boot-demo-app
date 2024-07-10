package ch.techstack.demo_rest_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.techstack.demo_rest_app.model.User;
import ch.techstack.demo_rest_app.service.UserService;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    private final static String text = "Foo bar baz user";
    private UserService userService;

    public UserController(UserService userService) {
        super();

        this.userService = userService;
    }

    @GetMapping("/test")
    public String registered() {
        return UserController.text;
    }

    @GetMapping("/validate/secret")
    public ResponseEntity<String> getSecret(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ) throws Exception {
        Optional<User> user = userService.findByEmailAndPassword(email, password);

        if(user.isPresent()) {
            return new ResponseEntity<>("API-Secret: " + user.get().getSecret(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Wrong credentials", HttpStatus.FORBIDDEN);

//        ResponseEntity<Boolean> hasUser = this.validate(email, password);
//
//        if(hasUser.getBody()) {
//            return new ResponseEntity<>("API-Secret: xxx", HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("Wrong credentials", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ) {
        Optional<User> user = userService.findByEmailAndPassword(email, password);

        return new ResponseEntity<>(user.isPresent(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserById(@RequestParam(value = "id") Long id) {
        User user = userService.findById(id);

        if(user == null) {
            return new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody() User user) {
        boolean hasUser = false;
        Iterable<User> userList = this.userService.findAll();

        for (User value : userList) {
            hasUser = value.getEmail().equals(user.getEmail());
        }

        if(!hasUser) {
            user.setSecret(UUID.randomUUID().toString());

            User newUser = this.userService.save(user);

            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(user, HttpStatus.CONFLICT);
    }
}
