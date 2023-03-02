package bot.sky.telegrambot.bot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import bot.sky.telegrambot.bot.TelegramBot;

/**
 * Данный класс инициализирует бота, создавая новую сессию при каждом запуске последнего.
 *
 * @author Мухаметзянов Эдуард
 */
@Slf4j
@Component
public class BotInitializer {
    final TelegramBot bot;

    public BotInitializer(TelegramBot bot) {
        this.bot = bot;
    }

    /**
     * Метод для инициализации телеграмм-бота
     *
     * @throws TelegramApiException
     * @author Мухаметзянов Эдуард
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка: " + e.getMessage());
        }
    }
}