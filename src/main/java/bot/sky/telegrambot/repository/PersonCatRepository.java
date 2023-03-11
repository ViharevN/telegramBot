package bot.sky.telegrambot.repository;

import model.PersonCat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PersonCatRepository extends JpaRepository<PersonCat, Long> {

    Set<PersonCat> findByChatId(Long chatId);
}
