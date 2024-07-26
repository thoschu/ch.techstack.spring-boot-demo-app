package ch.techstack.demo_rest_app.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

@RestController
public class FunRestController {
    private static final String hello = "Hello ";

    @Value("${developer.name}")
    private String name;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${team.name}")
    private String teamName;

    FunRestController() {
        String token = "eyJhbGciOiJIUzI1NiIsImN0eSI6IkpXVCJ9.eyJpc3MiOiJhdXRoMCJ9.mZ0m_N1J4PgeqWmi903JuUoDRZDBPB7HwkS4nVyWH1M";
        DecodedJWT jwt = JWT.decode(token);

        System.out.println(jwt.getToken());
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
}
