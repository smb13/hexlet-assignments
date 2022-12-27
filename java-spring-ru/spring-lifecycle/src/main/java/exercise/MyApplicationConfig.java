package exercise;

import java.time.LocalDateTime;

import exercise.daytimes.Daytime;
import exercise.daytimes.Morning;
import exercise.daytimes.Day;
import exercise.daytimes.Evening;
import exercise.daytimes.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// BEGIN
@Configuration
public class MyApplicationConfig {
    @Bean
    public Daytime createDaytimeBean(){
        Integer hour = LocalDateTime.now().getHour();
        if (hour < 6) {
            return new Night();
        } if (hour < 12) {
            return new Morning();
        } if (hour < 18) {
            return new Day();
        } else return new Evening();
    }
}
// END
