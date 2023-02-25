package bot.sky.telegrambot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "dogShelterUsers")
@Data
@NoArgsConstructor
public class RegisteredUserForDogShelter {

    @Id
    private Long chatId;

    private String name;

    private String phoneNumber;

    private String e_mail;
}
