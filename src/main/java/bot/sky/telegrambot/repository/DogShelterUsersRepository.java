package bot.sky.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import bot.sky.telegrambot.models.RegisteredUserForDogShelter;

public interface DogShelterUsersRepository extends CrudRepository<RegisteredUserForDogShelter, Long> {
}
