package exercise.controller;

import com.querydsl.core.types.Predicate;
import exercise.model.User;
import exercise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    // BEGIN
//    @GetMapping("")
//    public Iterable<User> getUsersByFilter(String firstName, String lastName) {
//        if (firstName != null && lastName != null) {
//            return userRepository.findAll(QUser.user.firstName.containsIgnoreCase(firstName).and(QUser.user.lastName.containsIgnoreCase(lastName)));
//        } else if (firstName != null && lastName == null) {
//            return userRepository.findAll(QUser.user.firstName.containsIgnoreCase(firstName));
//        } else if (firstName == null && lastName != null) {
//            return userRepository.findAll(QUser.user.lastName.containsIgnoreCase(lastName));
//        } else {
//            return userRepository.findAll();
//        }
//
//    }

    @GetMapping("")
    public Iterable<User> getUserCustomize(@QuerydslPredicate(root = User.class) Predicate predicate) {
        return userRepository.findAll(predicate);
    }
        // END
}

