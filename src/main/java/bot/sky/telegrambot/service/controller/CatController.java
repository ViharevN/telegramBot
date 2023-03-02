package bot.sky.telegrambot.service.controller;

import bot.sky.telegrambot.exceptions.CatNotFoundException;
import bot.sky.telegrambot.models.Cat;
import bot.sky.telegrambot.repository.CatRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatController {
    @Autowired
    private CatRepository repository;

    @Operation(summary = "Вывод полного списка котов из приюта.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Полный список котов.")
    })
    @GetMapping("/all")
    public List<Cat> getAllCats() {
        return repository.findAll();
    }

    @Operation(summary = "Поиск кота по id в списке приюта.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Найденный кот по задаваемому id")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<Cat> getCatById(@Parameter(description = "Введите id для поиска")
                                          @PathVariable long id) {
        Cat cat = repository.findById(id)
                .orElseThrow(() -> new CatNotFoundException("Cat not found by id: " + id));
        return ResponseEntity.ok().body(cat);
    }

    @Operation(summary = "Добавление нового кота в список приюта.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Кот добавлен в общий список приюта.")
    })
    @PostMapping("/add")
    public Cat addCat(@Parameter(description = "Введите данные кота") @RequestBody Cat cat) {
        return repository.save(cat);
    }

    @Operation(summary = "Изменение данных кота по id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Изменение данных прошло успешно.")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<Cat> updateCat(@Parameter(description = "Введите новые данные кота")
                                             @RequestBody Cat catNew,
                                         @Parameter(description = "Введите id кота")
                                         @PathVariable long id) {
        Cat cat = repository.findById(id)
                .orElseThrow(() -> new CatNotFoundException("Cat not found exist id: " + id));
        cat.setName(catNew.getName());
        cat.setBreed(catNew.getBreed());
        cat.setAge(catNew.getAge());

        repository.save(cat);
        return ResponseEntity.ok().body(cat);
    }

    @Operation(summary = "Удаление кота по id из списка приюта.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Удаления кота из списка прошло успешно.")
    })
    @DeleteMapping("/delete/{id}")
    public String deleteById(@Parameter(description = "Введите id")
                                 @PathVariable long id) {
        if (!repository.existsById(id)) {
            throw new CatNotFoundException("Cat not exist by id: " + id);
        }
        repository.deleteById(id);
        return "Cat with id: " + id + " has been deleted";
    }
}
