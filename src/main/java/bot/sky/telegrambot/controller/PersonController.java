package bot.sky.telegrambot.controller;

import bot.sky.telegrambot.exceptions.PersonNotFoundException;
import bot.sky.telegrambot.models.Person;
import bot.sky.telegrambot.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/all")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/by/{id}")
    public ResponseEntity<Person> getById(@PathVariable long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Human not exist by ID : "+ id));
        return ResponseEntity.ok().body(person);
    }

    @PostMapping("/add")
    public Person addPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person personDetails, @PathVariable long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not exist with id: " + id));
        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setRoles(personDetails.getRoles());
        Person person1 = personRepository.save(person);
        return ResponseEntity.ok().body(person1);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePersonById(@PathVariable Long id) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException("Not found user: " + id);
        }
        personRepository.deleteById(id);
        return "User: " + id + ", has been deleted";
    }

}
