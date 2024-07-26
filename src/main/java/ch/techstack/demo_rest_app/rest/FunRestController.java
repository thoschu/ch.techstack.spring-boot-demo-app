package ch.techstack.demo_rest_app.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {
    private static final String hello = "Hello ";

    @Value("${developer.name}")
    private String name;

    @Value("${spring.application.name}")
    private String applicationName;

    FunRestController() {
        System.out.println("--------------");
        System.out.println(applicationName);
    }

    // expose "/" that return "Hello World!!!"

    @GetMapping
    public String hello() {
        return FunRestController.hello + "World!";
    }

    @GetMapping("/name")
    public String developerName() {
        return applicationName + " by " + this.name;
    }
}
