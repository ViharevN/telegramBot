package bot.sky.telegrambot.service.controller;

import bot.sky.telegrambot.exceptions.DogNotFoundException;
import bot.sky.telegrambot.models.Dog;
import bot.sky.telegrambot.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dogs/")
public class DogController {
    @Autowired
    private DogRepository repository;

    @GetMapping("get/all")
    public List<Dog> getAllDogs() {
        return repository.findAll();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable long id) {
        Dog dog = repository.findById(id)
                .orElseThrow(() -> new DogNotFoundException("Dog not found exist ID: " + id));
        return ResponseEntity.ok().body(dog);
    }

    @PostMapping("/add")
    public Dog addDog(@RequestBody Dog dog) {
        return repository.save(dog);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Dog> updateDog(@RequestBody Dog dogNew, @PathVariable long id) {
        Dog dog = repository.findById(id)
                .orElseThrow(() -> new DogNotFoundException("Dog not found exist ID: " + id));

        dog.setName(dogNew.getName());
        dog.setBreed(dogNew.getBreed());
        dog.setAge(dogNew.getAge());
        repository.save(dog);
        return ResponseEntity.ok().body(dog);
    }

    @DeleteMapping("delete/{id}")
    public String deleteById(@PathVariable long id) {
        if (!repository.existsById(id)) {
            throw new DogNotFoundException("Dog not found, ID:  " + id);
        }
        repository.deleteById(id);
        return "Dog with id: " + id + " has been deleted";
    }
}
