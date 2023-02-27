package bot.sky.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import bot.sky.telegrambot.models.RegisteredUserForCatShelter;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для обслуживания (базовые CRUD операции) БД зарегистрированных пользователей приюта для кошек.
 */
@Repository
public interface CatShelterUsersRepository extends CrudRepository<RegisteredUserForCatShelter, Long> {
}
