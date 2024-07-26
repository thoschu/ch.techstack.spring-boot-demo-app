package ch.techstack.demo_rest_app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {
    private static final String hello = "Hello ";
    private final Coach coach;

    @Value("${developer.name}")
    private String name;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${team.name}")
    private String teamName;

    // @Autowired
    FunRestController(
            @Qualifier("tennisCoach") Coach coach
    ) {
        super();

        this.coach = coach;
    }

    // expose "/" that return "Hello World!!!"

    @GetMapping
    public String hello() {
        StringBuilder stringBuilder = new StringBuilder(100);

        stringBuilder.append(FunRestController.hello);
        stringBuilder.append("World!");

        return stringBuilder.toString();
    }

    @GetMapping("/name")
    public String developerName() {
        String sepBy = " by ";
        String sepAnd = " and: ";

        return applicationName
                .toUpperCase()
                .concat(sepBy)
                .concat(this.name)
                .concat(sepAnd)
                .concat(this.teamName);
    }

    @GetMapping("/coach")
    public String coach() {
        return this.coach.getDailyWorkout();
    }
}
