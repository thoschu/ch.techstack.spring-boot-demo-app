package ch.techstack.demo_rest_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
    private static final String hello = "Hello ";
    private final String textHelloWorld = TodoController.hello + "World!";

    @GetMapping
    public String hello() {
        return this.textHelloWorld;
    }

    // http://localhost:8080/greet?name=Tom&id=325
    @GetMapping("/greet")
    public String greeting(
            @RequestParam(value = "name") String identity,
            @RequestParam() String id
    ) {
        System.out.println(id);
        return TodoController.hello + identity;
    }

    @GetMapping("/say")
    public String say() {
        return "####";
    }
}
