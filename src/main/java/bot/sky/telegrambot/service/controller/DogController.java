package bot.sky.telegrambot.service.controller;

import bot.sky.telegrambot.exceptions.DogNotFoundException;
import bot.sky.telegrambot.models.Dog;
import bot.sky.telegrambot.repository.DogRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Вывод полного списка собак из приюта.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Полный список собак.")
    })
    @GetMapping("get/all")
    public List<Dog> getAllDogs() {
        return repository.findAll();
    }

    @Operation(summary = "Поиск собаки по id в списке приюта.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Найденная собака по задаваемому id")
    })
    @GetMapping("get/{id}")
    public ResponseEntity<Dog> getDogById(@Parameter(description = "Введите id для поиска")
                                              @PathVariable long id) {
        Dog dog = repository.findById(id)
                .orElseThrow(() -> new DogNotFoundException("Dog not found exist ID: " + id));
        return ResponseEntity.ok().body(dog);
    }

    @Operation(summary = "Добавление новой собаки в список приюта.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Собака добавлена в общий список приюта.")
    })
    @PostMapping("/add")
    public Dog addDog(@Parameter(description = "Введите данные собаки")
                          @RequestBody Dog dog) {
        return repository.save(dog);
    }

    @Operation(summary = "Изменение данных собаки по id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Изменение данных прошло успешно.")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<Dog> updateDog(@Parameter(description = "Введите новые данные собаки")
                                             @RequestBody Dog dogNew,
                                         @Parameter(description = "Введите id собаки")
                                         @PathVariable long id) {
        Dog dog = repository.findById(id)
                .orElseThrow(() -> new DogNotFoundException("Dog not found exist ID: " + id));

        dog.setName(dogNew.getName());
        dog.setBreed(dogNew.getBreed());
        dog.setAge(dogNew.getAge());
        repository.save(dog);
        return ResponseEntity.ok().body(dog);
    }

    @Operation(summary = "Удаление собаки по id из списка приюта.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Удаления собаки из списка прошло успешно.")
    })
    @DeleteMapping("delete/{id}")
    public String deleteById(@Parameter(description = "Введите id собаки")
                                 @PathVariable long id) {
        if (!repository.existsById(id)) {
            throw new DogNotFoundException("Dog not found, ID:  " + id);
        }
        repository.deleteById(id);
        return "Dog with id: " + id + " has been deleted";
    }
}
