package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    JdbcTemplate jdbc;

    @PostMapping(path = "")
    public void createPerson(@RequestBody Map<String, Object> person) {
        String query = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        jdbc.update(query, person.get("first_name"), person.get("last_name"));
    }

    // BEGIN
    @GetMapping(path = "", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getAllPeople()  {
        String query = "SELECT * FROM person";
        List<Map<String, Object>> ls = jdbc.queryForList(query);
        return ls;
    }

    @GetMapping(path = "/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getPersonById(@PathVariable String id) {
        String query = "SELECT * FROM person WhERE id=" + id;
        Map<String, Object> person = jdbc.queryForMap(query);
        return person;
    }
    // END
}
