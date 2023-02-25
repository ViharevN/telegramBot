package bot.sky.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import bot.sky.telegrambot.models.RegisteredUserForCatShelter;

public interface CatShelterUsersRepository extends CrudRepository<RegisteredUserForCatShelter, Long> {
}
