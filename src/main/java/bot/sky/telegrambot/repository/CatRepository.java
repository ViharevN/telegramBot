package bot.sky.telegrambot.repository;

import model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс Cat Repository.
 * @автор Хотенов Василий
 */
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}