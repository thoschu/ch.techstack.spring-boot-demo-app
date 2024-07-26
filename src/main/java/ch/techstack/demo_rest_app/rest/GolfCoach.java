package ch.techstack.demo_rest_app.rest;

import org.springframework.stereotype.Component;

@Component("golfCoach")
public class GolfCoach implements Coach {

    /**
     * @return Practise Golf
     */
    @Override
    public String getDailyWorkout() {
        return "Practise putting for 15 minutes";
    }
}
