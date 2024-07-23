package ch.techstack.demo_rest_app.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.techstack.demo_rest_app.model.User;
import ch.techstack.demo_rest_app.service.UserService;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/test")
    public ResponseEntity<Void> test(@RequestBody() User user, UriComponentsBuilder ucb) throws URISyntaxException {
        // Iterable<User> userList = this.userService.findAll();
        System.out.println(user);

        URI locationOfNewUser = ucb
                .path("user/{id}")
                .buildAndExpand("77")
                .toUri();

        return ResponseEntity.created(locationOfNewUser).build();
    }

    @GetMapping("/validation/secret")
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

    @GetMapping("/validation")
    public ResponseEntity<Boolean> validate(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ) {
        Optional<User> user = userService.findByEmailAndPassword(email, password);

        return new ResponseEntity<>(user.isPresent(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);

        if(user == null) {
            return new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody() User user, UriComponentsBuilder ucb) throws URISyntaxException {
        boolean hasUser = false;
        Iterable<User> userList = this.userService.findAll();

        for (User value : userList) {
            String email = user.getEmail();
            hasUser = value.getEmail().equals(email);

            if(hasUser) break;
        }

        if(!hasUser) {
            user.setSecret(UUID.randomUUID().toString());

            User newUser = this.userService.save(user);

            URI locationOfNewUser = ucb
                    .path("user/{id}")
                    .buildAndExpand(newUser.getId())
                    .toUri();

//            return ResponseEntity
//                    .status(HttpStatus.CREATED)
//                    .header(HttpHeaders.LOCATION, new URI("/user/" + newUser.getId()).toASCIIString())
//                    .build();
//            return ResponseEntity.created(new URI("/user/" + newUser.getId())).build();
//            return ResponseEntity.created(URI.create("/user/" + newUser.getId())).build();

            return ResponseEntity.created(locationOfNewUser).build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
