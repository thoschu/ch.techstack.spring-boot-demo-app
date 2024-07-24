package ch.techstack.demo_rest_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

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
