package ch.techstack.demo_rest_app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
    private static final String hello = "Hello ";

    @GetMapping
    public String hello() {
        return TodoController.hello + "World!";
    }

    @GetMapping("/greet")
    public String greeting() {
        return TodoController.hello + "User";
    }
}
