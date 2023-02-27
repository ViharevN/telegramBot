package bot.sky.telegrambot.bot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/** Данный класс содержит имя бота и токен бота.
 * Данные для полей класса получаются из конфигурационного файла application.properties
 * @author Мухаметзянов Эдуард
 */

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {
        @Value("${bot.name}")
        String botName;
        @Value("${bot.token}")
        String token;
}
