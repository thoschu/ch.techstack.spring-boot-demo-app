package ch.techstack.demo_rest_app.rest;

import org.springframework.stereotype.Component;

@Component("tennisCoach")
public class TennisCoach implements Coach {

    /**
     * @return Practise Tennis
     */
    @Override
    public String getDailyWorkout() {
        return "Practise backhand for 30 minutes";
    }
}
