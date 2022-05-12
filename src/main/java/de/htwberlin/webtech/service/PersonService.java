package de.htwberlin.webtech.service;

import de.htwberlin.webtech.web.api.Person;
import de.htwberlin.webtech.persistence.PersonEntity;
import de.htwberlin.webtech.persistence.PersonRepository;
import de.htwberlin.webtech.web.api.PersonManipulationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        List<PersonEntity> persons = personRepository.findAll();
        return persons.stream()
                .map(this::fromPersonEntityToPerson)
                .collect(Collectors.toList());
    }

    public Person findById(Long id) {
        var personEntity = personRepository.findById(id);
        return personEntity.map(this::fromPersonEntityToPerson).orElse(null);
    }

    public Person create(PersonManipulationRequest request) {
        var personEntity = new PersonEntity(request.getFirstName(), request.getLastName(), request.isVaccinated());
        personEntity = personRepository.save(personEntity);
        return fromPersonEntityToPerson(personEntity);
    }

    public Person update(Long id, PersonManipulationRequest request) {
        var personEntityOptional = personRepository.findById(id);
        if (personEntityOptional.isEmpty()) return null;

        var personEntity = personEntityOptional.get();
        personEntity.setFirstName(request.getFirstName());
        personEntity.setLastName(request.getLastName());
        personEntity.setVaccinated(request.isVaccinated());
        personEntity = personRepository.save(personEntity);

        return fromPersonEntityToPerson(personEntity);
    }

    private Person fromPersonEntityToPerson(PersonEntity pe) {
        return new Person(
                pe.getId(),
                pe.getFirstName(),
                pe.getLastName(),
                pe.isVaccinated()
        );
    }
}
