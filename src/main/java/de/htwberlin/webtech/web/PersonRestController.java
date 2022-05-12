package de.htwberlin.webtech.web;

import de.htwberlin.webtech.web.api.Person;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonRestController {

    private List<Person> persons;

    public PersonRestController() {
        persons = new ArrayList<>();
        persons.add(new Person(1, "Zinedine","Zidane",false));
        persons.add(new Person(2, "Emma","Raducanu",true));
    }

    @GetMapping(path = "/api/v1/persons")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Person> fetchPersons() {
        return persons;
    }
}
