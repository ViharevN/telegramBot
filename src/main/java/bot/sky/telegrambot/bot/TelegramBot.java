package bot.sky.telegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import bot.sky.telegrambot.bot.config.BotConfig;
import bot.sky.telegrambot.bot.service.BotMenuCreator;
import bot.sky.telegrambot.bot.service.CommandSelector;
import bot.sky.telegrambot.models.Visitor;
import bot.sky.telegrambot.repository.VisitorsRepository;


@SuppressWarnings("deprecation")
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig botConfig;
    final VisitorsRepository visitorsRepository;
    final BotMenuCreator botMenuCreator;

    public TelegramBot(BotConfig botConfig, VisitorsRepository visitorsRepository, BotMenuCreator botMenuCreator) {
        this.botConfig = botConfig;
        this.visitorsRepository = visitorsRepository;
        this.botMenuCreator = botMenuCreator;
        try {
            this.execute(new SetMyCommands(
                    BotMenuCreator.addCommandsToBotMenu(),
                    new BotCommandScopeDefault(),
                    null));
        } catch (TelegramApiException e) {
            log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        registerVisitor(update);
        var chatId = update.getMessage().getChatId();
        Visitor visitor = visitorsRepository.findById(chatId).get();
        String inputText = update.getMessage().getText();
        //Если посетитель закончил общение в каком-то разделе меню,
        //то это меню и будет отображено ботом
        //Закомментировал - пока не работает корректный выбор меню:
        //если получать последний сохраненный раздел меню, то на новые варианты меню не реагирует
        //if (visitor.getLastCommand() != null){
        //    inputText = visitor.getLastCommand();
        //}
        String outputText = new CommandSelector(visitorsRepository).selectBotCommand(inputText, visitor);
        sendMessage(chatId, outputText);
    }

    /**
     * Метод для авто-регистрации посетителей чата.
     * Сохраняется chatId и имя посетителя.
     * @param update
     */
    private Visitor registerVisitor(Update update) {
        Visitor visitor = new Visitor();
        var chatId = update.getMessage().getChatId();
        var name = update.getMessage().getChat().getFirstName();
        visitor.setName(name);
        visitor.setChatId(chatId);

        if (!visitorsRepository.existsById(chatId)) {
            visitorsRepository.save(visitor);
            log.info("Посетитель " + name + " добавлен в БД посетителей");
        } else {
            log.info("Посетитель " + name + " уже записан в БД посетителей");
        }
        return visitor;
    }

    /**
     * Метод для отправки сообщений.
     * Метод принимает два аргумента - chatId (для идентификации собеседника) и textToSend (текст сообщения)
     * @param chatId
     * @param textToSend
     */
    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        //Чтобы отправить сообщение, надо указать кому будем отправлять - а именно его chatId
        message.setChatId(chatId);
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка: " + e.getMessage());
        }
    }

    //Здесь два обязательных метода для получения имени и токена бота
    //Без корректной работы этих методов соединения с ботом НЕ БУДЕТ
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @SuppressWarnings("deprecation")
    public String getBotToken() {
        return botConfig.getToken();
    }
}