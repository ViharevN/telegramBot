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
    public Person updatePerson(@RequestBody Person person, @PathVariable long id) {
        Optional<Person> person1 = personRepository.findById(id);
        if (person.equals(person1)) {
            return personRepository.save(person);
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public Person deletePersonById(@PathVariable Long id) {
        personRepository.deleteById(id);
        return personRepository.getById(id);
    }

}
