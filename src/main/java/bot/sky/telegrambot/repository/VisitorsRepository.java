package bot.sky.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import bot.sky.telegrambot.models.Visitor;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для обслуживания (базовые CRUD операции) БД незарегистрированных пользователей приложения (бота).
 */
@Repository
public interface VisitorsRepository extends CrudRepository<Visitor, Long> {
}
