package bot.sky.telegrambot.models;

//Класс тестовый, создан только для работы над Меню
//После создания настоящего класса User - удалить !!!

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "userForTesting")
@Data
@NoArgsConstructor
public class TestUserForMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    private String nameInChat;

    private String firstName;

    private String lastName;

    private String visitedShelter;

    private String phoneNumber;

    private String e_mail;

    private boolean isRegistered;

    //поле для хранения правда/ложь по наличию взятого питомца
    private boolean isHavePet;

    private InnerStatusUser innerStatusUser;

}


