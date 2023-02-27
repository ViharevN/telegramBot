package bot.sky.telegrambot.service.controller;

import bot.sky.telegrambot.exceptions.DogNotFoundException;
import bot.sky.telegrambot.models.Dog;
import bot.sky.telegrambot.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Dog getDogById(@PathVariable long id) {
        if (!repository.existsById(id)) {
            throw new DogNotFoundException("Dog not exist by id: " + id);
        }
        return repository.getById(id);

    }

}
