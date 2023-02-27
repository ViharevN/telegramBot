package bot.sky.telegrambot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс-сущность для создания на его основе таблицы в БД для учета желающих взять из приюта для собак питомца.
 *
 * @author Мухаметзянов Эдуард
 */

@Entity(name = "dogShelterUsers")
@Data
@NoArgsConstructor
public class RegisteredUserForDogShelter {

    @Id
    private Long chatId;

    private String nameInChat;

    private String firstName;

    private String lastName;

    private String visitedShelter;

    private String phoneNumber;

    private String e_mail;
}
