package ch.techstack.demo_rest_app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

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
        return id + " " + TodoController.hello + identity;
    }

    // http://localhost:8080/hi?name=admin
    @GetMapping("/hi")
    public ResponseEntity<String> hi(@RequestParam() String name) {
        // System.out.println(name);

        if(name.equals("admin"))
            return new ResponseEntity<>("Hi " + name, HttpStatus.OK);

        return new ResponseEntity<>("Error: " + name, HttpStatus.BAD_REQUEST);
    }
}
