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


}
