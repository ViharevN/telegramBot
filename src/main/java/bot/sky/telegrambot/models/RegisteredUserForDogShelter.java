package bot.sky.telegrambot.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс-сущность для создания на его основе таблицы в БД для учета желающих взять из приюта для собак питомца.
 */

@Entity(name = "dogShelterUsers")
@Data
@NoArgsConstructor
public class RegisteredUserForDogShelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;
    @Column(name = "nameInChat")
    private String nameInChat;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "visitedShelter")
    private String visitedShelter;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "email")
    private String e_mail;

    public RegisteredUserForDogShelter(String nameInChat, String firstName, String lastName, String visitedShelter, String phoneNumber, String e_mail) {
        this.nameInChat = nameInChat;
        this.firstName = firstName;
        this.lastName = lastName;
        this.visitedShelter = visitedShelter;
        this.phoneNumber = phoneNumber;
        this.e_mail = e_mail;
    }
}
