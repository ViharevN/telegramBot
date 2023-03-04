package bot.sky.telegrambot.repository;

import bot.sky.telegrambot.models.QuestionFromUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends CrudRepository<QuestionFromUser, Long> {
}
