package bot.sky.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import bot.sky.telegrambot.models.Visitor;

/**
 * Интерфейс для обслуживания (базовые CRUD операции) БД незарегистрированных пользователей приложения (бота).
 *
 * @author Мухаметзянов Эдуард
 */
public interface VisitorsRepository extends CrudRepository<Visitor, Long> {
}
