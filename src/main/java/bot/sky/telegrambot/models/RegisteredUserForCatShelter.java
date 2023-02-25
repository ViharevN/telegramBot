package bot.sky.telegrambot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "catShelterUsers")
@Data
@NoArgsConstructor
public class RegisteredUserForCatShelter {

    @Id
    private Long chatId;

    private String name;

    private String phoneNumber;

    private String e_mail;
}
