package bot.sky.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import bot.sky.telegrambot.models.RegisteredUserForCatShelter;

/**
 * Интерфейс для обслуживания (базовые CRUD операции) БД зарегистрированных пользователей приюта для кошек.
 *
 * @author Мухаметзянов Эдуард
 */
public interface CatShelterUsersRepository extends CrudRepository<RegisteredUserForCatShelter, Long> {
}
