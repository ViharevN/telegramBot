package bot.sky.telegrambot.controller;

import bot.sky.telegrambot.exceptions.DogNotFoundException;
import bot.sky.telegrambot.models.Dog;
import bot.sky.telegrambot.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dogs")
public class DogController {
    @Autowired
    private DogRepository dogRepository;

    @GetMapping("/all")
    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Dog> getDogById(long id) {
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new DogNotFoundException("Dog not exist by ID: " + id));
        return ResponseEntity.ok().body(dog);
    }

    @PostMapping("/add")
    public Dog addDog(@RequestBody Dog dog) {
        return dogRepository.save(dog);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Dog> updateDog(@RequestBody Dog dogDetails, @PathVariable long id) {
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new DogNotFoundException("Dog not exist by ID: " + id));
        dog.setName(dogDetails.getName());
        dog.setBreed(dogDetails.getBreed());
        dog.setAge(dogDetails.getAge());
        Dog dog1 = dogRepository.save(dog);
        return ResponseEntity.ok().body(dog1);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDog(@PathVariable long id) {
        if (!dogRepository.existsById(id)) {
            throw new DogNotFoundException("Dog not found exception by id: " + id);
        }
        dogRepository.deleteById(id);
        return "Dog has been deleted";
    }
}
