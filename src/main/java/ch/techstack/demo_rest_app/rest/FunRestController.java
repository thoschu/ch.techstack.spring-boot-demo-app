package ch.techstack.demo_rest_app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {
    private static final String hello = "Hello ";

    // expose "/" that return "Hello World!!!"

    @GetMapping
    public String hello() {
        return FunRestController.hello + "World!!!";
    }
}
