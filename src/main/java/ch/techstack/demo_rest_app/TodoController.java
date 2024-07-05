package ch.techstack.demo_rest_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
    private static final String hello = "Hello ";
    private final String textHelloWorld = TodoController.hello + "World!";

    @GetMapping
    public String hello() {
        return this.textHelloWorld;
    }

    @GetMapping("/greet")
    public String greeting() {
        return TodoController.hello + "User";
    }
}
