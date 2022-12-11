package exercise.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// BEGIN
@Entity
@Table(name="person")
// END
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // BEGIN
    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // END
}
