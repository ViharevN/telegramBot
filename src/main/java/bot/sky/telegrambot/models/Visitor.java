package bot.sky.telegrambot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Класс-сущность для создания на его основе таблицы в БД для учета скрытой регистрации пользователей чата.
 * Сохраняются следующие данные:
 * - Id пользователя в приложении Telegram-приложении
 * - имя пользователя, указанное в приложении Telegram-приложении
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

    private Message message;

    public Visitor(String nameInChat, String visitedShelter, String lastCommand) {
        this.nameInChat = nameInChat;
        this.visitedShelter = visitedShelter;
        this.lastCommand = lastCommand;
    }

    public Visitor(Long chatId, String nameInChat, String visitedShelter, String lastCommand, Message message) {
        this.chatId = chatId;
        this.nameInChat = nameInChat;
        this.visitedShelter = visitedShelter;
        this.lastCommand = lastCommand;
        this.message = message;
    }

    public Visitor(Long chatId, String nameInChat, String visitedShelter, String lastCommand) {
        this.chatId = chatId;
        this.nameInChat = nameInChat;
        this.visitedShelter = visitedShelter;
        this.lastCommand = lastCommand;

    }

    public Long getChatId() {
        return chatId;
    }

    public static void setChatId(Long chatId) {

    }
}
