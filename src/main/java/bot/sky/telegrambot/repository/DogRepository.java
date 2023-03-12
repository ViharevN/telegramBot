package bot.sky.telegrambot.repository;

import bot.sky.telegrambot.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Interface DogRepository.
 * @author Решетов Юра
 * @version 1.0.0
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}