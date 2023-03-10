package configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands;
import org.telegram.telegrambots.meta.generics.TelegramBot;

/**
 * @author Alexey Kuzminykh
 * @version 1.1.1
 */
@Configuration
@EnableScheduling
public class TelegramBotConfiguration {

    @Value("6236177694:AAEmhn2TEUG8bWr2e0r2FVGlJipcbwTmfKI")
    private String token;

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        return bot;
    }



}
