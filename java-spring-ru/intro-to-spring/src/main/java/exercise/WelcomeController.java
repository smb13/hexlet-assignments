package exercise;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// BEGIN
@RestController
public class WelcomeController {
    @GetMapping ("/")
    public String welcome(){
        return "Welcome to Spring";
    }

    @GetMapping ("/hello")
    public String hello(@RequestParam Optional<String> name){
        return "Hello, " + name.orElseGet(() -> "World") + "!";
    }
}
// END
