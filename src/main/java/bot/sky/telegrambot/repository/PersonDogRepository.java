package bot.sky.telegrambot.repository;

import bot.sky.telegrambot.model.PersonDog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;


/**
 * Interface PersonDogRepository.   *
 */
@Repository
public interface PersonDogRepository extends JpaRepository<PersonDog, Long> {
    Set<PersonDog> findByChatId(Long chatId);
}
