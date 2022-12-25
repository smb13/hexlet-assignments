package exercise.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "users")
@EnableSpringDataWebSupport
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String gender;

    private String profession;
}
