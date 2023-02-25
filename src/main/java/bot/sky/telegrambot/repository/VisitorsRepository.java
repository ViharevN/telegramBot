package bot.sky.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import bot.sky.telegrambot.models.Visitor;

public interface VisitorsRepository extends CrudRepository<Visitor, Long> {
}
