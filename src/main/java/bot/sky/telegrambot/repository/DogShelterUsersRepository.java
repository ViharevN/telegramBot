package bot.sky.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import bot.sky.telegrambot.models.RegisteredUserForDogShelter;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для обслуживания (базовые CRUD операции) БД зарегистрированных пользователей приюта для собак.
 */
@Repository
public interface DogShelterUsersRepository extends CrudRepository<RegisteredUserForDogShelter, Long> {

}
