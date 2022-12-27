package exercise;

import exercise.daytimes.Daytime;
import exercise.daytimes.Morning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
public class WelcomeController {
    @Autowired
    Daytime daytime;
    @Autowired
    Meal meal;

    @GetMapping("/daytime")
    public String getWelcomePhrase(){
        return "It is " + daytime.getName() + " now. Enjoy your " + meal.getMealForDaytime(daytime.getName());
    }
}
// END
