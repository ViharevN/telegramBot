package bot.sky.telegrambot.controller;

import bot.sky.telegrambot.exceptions.CatNotFountException;
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
    private CatRepository catRepository;

    @GetMapping("/all")
    public List<Cat> getAllCats() {
        return catRepository.findAll();
    }

    @GetMapping("by/{id}")
    public ResponseEntity<Cat> gatCatById(long id) {
        Cat cat = catRepository.findById(id)
                .orElseThrow(() -> new CatNotFountException("Cat not exist by id: " + id));
        return ResponseEntity.ok().body(cat);
    }

    @PostMapping("/add")
    public Cat addCat(@RequestBody Cat cat) {
        return catRepository.save(cat);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Cat> updateCat(@RequestBody Cat catDetails, @PathVariable long id) {
        Cat cat = catRepository.findById(id)
                .orElseThrow(() -> new CatNotFountException("Cat not exist by ID: " + id));
        cat.setCatName(catDetails.getCatName());
        cat.setBreed(catDetails.getBreed());
        cat.setAge(catDetails.getAge());
        return ResponseEntity.ok().body(cat);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCAtById(@PathVariable long id) {
        if (!catRepository.existsById(id)) {
            throw new CatNotFountException("Cat not exist by ID: " + id);
        }
        catRepository.deleteById(id);
        return "Cat has been deleted: " + id;
    }
}
