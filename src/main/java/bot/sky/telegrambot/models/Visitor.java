package bot.sky.telegrambot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "visitorsTable")
@Data
@NoArgsConstructor
public class Visitor {

    @Id
    private Long chatId;

    private String name;

    private String visitedShelter;

    private String lastCommand;
}
