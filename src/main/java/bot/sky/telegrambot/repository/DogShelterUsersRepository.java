package bot.sky.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import bot.sky.telegrambot.models.RegisteredUserForDogShelter;

/**
 * Интерфейс для обслуживания (базовые CRUD операции) БД зарегистрированных пользователей приюта для собак.
 *
 * @author Мухаметзянов Эдуард
 */
public interface DogShelterUsersRepository extends CrudRepository<RegisteredUserForDogShelter, Long> {
}
