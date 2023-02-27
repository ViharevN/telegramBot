package bot.sky.telegrambot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс-сущность для создания на его основе таблицы в БД для учета скрытой регистрации пользователей чата.
 * Сохраняются следующие данные:
 * - Id пользователя в приложении Telegram-приложении
 * - имя пользователя, указанное в приложении Telegram-приложении
 *
 * @author Мухаметзянов Эдуард
 */

@Entity(name = "visitorsTable")
@Data
@NoArgsConstructor
public class Visitor {
    @Id
    private Long chatId;

    private String nameInChat;

    private String visitedShelter;

    private String lastCommand;
}
