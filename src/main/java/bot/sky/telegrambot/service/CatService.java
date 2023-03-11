package bot.sky.telegrambot.service;

import bot.sky.telegrambot.exceptions.CatNotFoundException;
import bot.sky.telegrambot.repository.CatRepository;
import model.Cat;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * * Класс CatService.
 * @автор Хотенов Василий
 */
@Service
public class CatService {

    private final CatRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(CatService.class);

    public CatService(CatRepository repository) {
        this.repository = repository;
    }

    /**
     * Метод поиска кота по id.
     * @param id
     * @return {@link CatRepository#findById(Object)}
     * @see CatService
     * @exception CatNotFoundException
     */
    public Cat getById(Long id) {
        logger.info("Was invoked method to get a cat by id={}", id);
        return this.repository.findById(id)
                .orElseThrow(CatNotFoundException::new);
    }

    /**
     * Метод создания кота.
     * @param cat
     * @return {@link CatRepository#save(Object)}
     * @see CatService
     */
    public Cat create(Cat cat) {
        logger.info("Was invoked method to create a cat");

        return this.repository.save(cat);
    }

    /**
     * Метод обновления данных о коте.
     * @param cat
     * @return {@link CatRepository#save(Object)}
     * @see CatService
     * @exception CatNotFoundException
     */
    public Cat update(Cat cat) {
        logger.info("Was invoked method to update a cat");

        if (cat.getId() != null) {
            if (getById(cat.getId()) != null) {
                return this.repository.save(cat);
            }
        }
        throw new CatNotFoundException();
    }

    /**
     * Метод получения всех котов.
     * @return {@link CatRepository#findAll()}
     * @see CatService
     */
    public Collection<Cat> getAll() {
        logger.info("Was invoked method to get all cats");

        return this.repository.findAll();
    }

    /**
     * Метод удаления кота по id.
     * @param id
     */
    public void removeById(Long id) {
        logger.info("Was invoked method to remove a cat by id={}", id);

        this.repository.deleteById(id);
    }

}
