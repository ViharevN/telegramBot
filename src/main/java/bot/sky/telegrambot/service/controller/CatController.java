package bot.sky.telegrambot.service.controller;

import bot.sky.telegrambot.exceptions.CatNotFoundException;
import bot.sky.telegrambot.models.Cat;
import bot.sky.telegrambot.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {
    @Autowired
    private CatRepository repository;

    @GetMapping("/all")
    public List<Cat> getAllCats() {
        return repository.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Cat> getCatById(@PathVariable long id) {
        Cat cat = repository.findById(id)
                .orElseThrow(() -> new CatNotFoundException("Cat not found by id: " + id));
        return ResponseEntity.ok().body(cat);
    }

    @PostMapping("/add")
    public Cat addCat(@RequestBody Cat cat) {
        return repository.save(cat);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Cat> updateCat(@RequestBody Cat catNew, @PathVariable long id) {
        Cat cat = repository.findById(id)
                .orElseThrow(() -> new CatNotFoundException("Cat not found exist id: " + id));
        cat.setName(catNew.getName());
        cat.setBreed(catNew.getBreed());
        cat.setAge(catNew.getAge());

        repository.save(cat);
        return ResponseEntity.ok().body(cat);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable long id) {
        if (!repository.existsById(id)) {
            throw new CatNotFoundException("Cat not exist by id: " + id);
        }
        repository.deleteById(id);
        return "Cat with id: " + id + " has been deleted";
    }
}
