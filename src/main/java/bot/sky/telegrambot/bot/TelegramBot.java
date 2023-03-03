package bot.sky.telegrambot.bot;

import bot.sky.telegrambot.models.*;
import bot.sky.telegrambot.repository.TestUsersRepository;
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


/**
 * Один из основных классов приложения - класс следит за обновлением строки чата,
 * и, если пользователь что-то ввел, то обрабатывает полученные данные.
 * Класс содержит приватные методы по созданию экземпляров классов Visitor и
 * осуществляет регистрацию пользователей приюта для собак и приюта для кошек.
 *
 * @author Мухаметзянов Эдуард
 */

// Класс нуждается в доработке:
// - надо добавить вызов метода сохранения отчета пользователя
// - надо добавить вызов метода (-ов) для обеспечения работы волонтера

@SuppressWarnings("deprecation")
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig botConfig;
    final VisitorsRepository visitorsRepository;
    final DogShelterUsersRepository dogShelterUsersRepository;
    final CatShelterUsersRepository catShelterUsersRepository;
    final BotMenuCreator botMenuCreator;
    final CommandSelector commandSelector;

    final TestUsersRepository testUsersRepository;


    public TelegramBot(BotConfig botConfig,
                       VisitorsRepository visitorsRepository,
                       DogShelterUsersRepository dogShelterUsersRepository,
                       CatShelterUsersRepository catShelterUsersRepository,
                       BotMenuCreator botMenuCreator,
                       CommandSelector commandSelector,
                       TestUsersRepository testUsersRepository) {
        this.botConfig = botConfig;
        this.visitorsRepository = visitorsRepository;
        this.dogShelterUsersRepository = dogShelterUsersRepository;
        this.catShelterUsersRepository = catShelterUsersRepository;
        this.botMenuCreator = botMenuCreator;
        this.commandSelector = commandSelector;
        this.testUsersRepository = testUsersRepository;
        try {
            this.execute(new SetMyCommands(
                    botMenuCreator.addCommandsForNewUser(),
                    new BotCommandScopeDefault(),
                    null));
        } catch (TelegramApiException e) {
            log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
        }
    }

    //Этот метод надо глубоко переработать!
    @Override
    public void onUpdateReceived(Update update) {
        //Получаем chatId - уникальный идентификатор пользователя
        //т.е. узнаем кто нам написал
        Long chatId = update.getMessage().getChatId();
        //Ищем в БД такого пользователя, и уже исходя из его статуса строится дальнейшее общение
        //И обновляем Меню для пользователя, соответствующее его статусу
        //Если пользователь уже есть в БД, то меняем ему отображаемое Меню
        if (testUsersRepository.existsById(chatId)) {
            selectMenuForUser(chatId);
        }
        //А если такого пользователя нет, то выводим приветствие и проводим первичную (скрытую) регистрацию
        //Меню для такого пользователя меняем после
        else {
            String nameInChat = update.getMessage().getChat().getFirstName();
            //String textToSend = "Здравствуйте " + nameInChat + "!\n\n" +
            //"Пожалуйста, выберите команду /start в Menu";
            silentRegistration(chatId, nameInChat);
            log.info("Новый пользователь " + nameInChat + " зарегистрирован как посетитель");
        }

        //Так, меню для пользователя мы обновили.
        //Теперь начнем обрабатывать поступающие сообщения
        //Чаще всего будут поступать выбранные пользователем команды из Меню
        //Значит их обработаем в первую очередь.
        //Получаем текст, который ввел пользователь
        String inputText = update.getMessage().getText();
        //Если введенный текст начинается с "/", то сразу отправляем на обработку
        if (inputText.startsWith("/")) {
            String outputText = commandSelector.selectBotCommand(inputText);
            sendMessage(chatId, outputText);
        }
        //Если текст начинается с "рег:", то значит поступили регистрационные данные
        if (inputText.startsWith("рег:")) {
            if (registerUser(update)) {
                sendMessage(chatId, commandSelector.selectBotCommand("/registration_user_complete"));
            } else {
                sendMessage(chatId, commandSelector.selectBotCommand("/registration_user_error"));
            }

        }
        //Если текст начинается с секретного кода ("/dgrgrhtr"), то это заходит в чат волонтер
        //И для него надо обновить волонтерское меню
        if (inputText.startsWith("/dgrgrhtr")) {
            try {
                execute(new SetMyCommands(
                        botMenuCreator.addCommandsForVolunteer(),
                        new BotCommandScopeDefault(),
                        null));
            } catch (TelegramApiException e) {
                log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
            }
        }

        if (inputText.equals("/dog_shelter")) {
            if (testUsersRepository.findById(chatId).isPresent()) {
                TestUserForMenu user = testUsersRepository.findById(chatId).get();
                user.setVisitedShelter("/dog_shelter");
                testUsersRepository.save(user);
                log.info("Пользователь выбрал dog_shelter");
            }
        }
        if (inputText.equals("/cat_shelter")) {
            if (testUsersRepository.findById(chatId).isPresent()) {
                TestUserForMenu user = testUsersRepository.findById(chatId).get();
                user.setVisitedShelter("/cat_shelter");
                testUsersRepository.save(user);
                log.info("Пользователь выбрал cat_shelter");
            }
        }

        //Здесь же надо прописать обработку полученной фотографии питомца, которая является частью отчета

        //Если текст начинается с "отчет:", то значит пользователь добавляет отчетные данные
        //Надо продумать как маркировать команды, которые связаны с отправкой отчета !!!!

    }

    private void selectMenuForUser(Long chatId) {
        //Для удобства получим ранее зарегистрированного в БД пользователя
        TestUserForMenu testUserForMenu = testUsersRepository.findById(chatId).get();
        //Узнаем его внутренний статус и заполняем Меню под него
        //Сгруппируем по приютам
        if (testUserForMenu.getVisitedShelter().equals("/dog_shelter")) {
            if (testUserForMenu.getInnerStatusUser() == InnerStatusUser.USER_WITH_PET) {
                try {
                    execute(new SetMyCommands(
                            botMenuCreator.addCommandsForRegisteredUsersAfterAdoptDog(),
                            new BotCommandScopeDefault(),
                            null));
                } catch (TelegramApiException e) {
                    log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
                }
            } else {
                try {
                    execute(new SetMyCommands(
                            botMenuCreator.addCommandsForDogShelterUsers(),
                            new BotCommandScopeDefault(),
                            null));
                } catch (TelegramApiException e) {
                    log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
                }
            }
        }

        if (testUserForMenu.getVisitedShelter().equals("/cat_shelter")) {
            if (testUserForMenu.getInnerStatusUser() == InnerStatusUser.USER_WITH_PET) {
                try {
                    execute(new SetMyCommands(
                            botMenuCreator.addCommandsForRegisteredUsersAfterAdoptDog(),
                            new BotCommandScopeDefault(),
                            null));
                } catch (TelegramApiException e) {
                    log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
                }
            } else {
                try {
                    execute(new SetMyCommands(
                            botMenuCreator.addCommandsForCatShelterUsers(),
                            new BotCommandScopeDefault(),
                            null));
                } catch (TelegramApiException e) {
                    log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
                }
            }
        }
    }

    private void silentRegistration(Long chatId, String nameInChat) {
        TestUserForMenu testUserForMenu = new TestUserForMenu();
        testUserForMenu.setChatId(chatId);
        testUserForMenu.setNameInChat(nameInChat);
        testUserForMenu.setInnerStatusUser(InnerStatusUser.NOT_REGISTERED_USER);
        testUsersRepository.save(testUserForMenu);
        log.info("Новый пользователь " + nameInChat + " записан в БД");
    }

    /**
     * Метод для регистрации пользователей, которые планируют взять питомца из приюта.
     * По итогам работы метода может быть создан один из двух зарегистрированных пользователей
     * (для каждого приюта) или не создан, если введены некорректные регистрационные данные.
     *
     * @author Мухаметзянов Эдуард
     */
    private boolean registerUser(Update update) {
        //Соберем значения полей, чтобы потом их присвоить
        Long newChatId = update.getMessage().getChatId();
        String newNameInChat = update.getMessage().getChat().getFirstName();
        String selectedShelter = testUsersRepository.findById(newChatId).get().getVisitedShelter();
        //Получаем строку из сообщения
        String text = update.getMessage().getText();
        //Удалим пробелы внутри строки
        text = text.replaceAll(" ", "");
        //Теперь делим полученную строку на отдельные слова
        String[] words = text.split(";");
        //Если после деления на слова получилось меньше 4 элементов, то во введенной строке чего-то не хватает,
        //а значит и данные для регистрации введены неправильно
        if (words.length != 4) {
            return false;
        }
        //Теперь из массива получим имя, фамилию, номер телефона и почту
        String newFirstName = words[0];
        String newLastName = words[1];
        String newPhoneNumber = words[2];
        String newEmail = words[3];

        //Значения всех будущих полей получены, создаем экземпляр класса и заполняем его поля.
        TestUserForMenu newUser = new TestUserForMenu();
        newUser.setChatId(newChatId);
        newUser.setNameInChat(newNameInChat);
        newUser.setFirstName(newFirstName);
        newUser.setLastName(newLastName);
        newUser.setPhoneNumber(newPhoneNumber);
        newUser.setE_mail(newEmail);
        newUser.setVisitedShelter(selectedShelter);
        newUser.setInnerStatusUser(InnerStatusUser.REGISTERED_USER);
        //Сохраняем экземпляр в БД
        testUsersRepository.save(newUser);
        //Возвращаем логическое значение
        //Если в БД есть пользователь с таким chatId, то вернется true
        //Если нет - вернется false
        boolean userIsCreated = testUsersRepository.existsById(newChatId);
        if (userIsCreated) {
            log.info("Регистрация завершена успешно!");
        } else {
            log.error("Регистрация не удалась...");
        }
        return userIsCreated;
    }


    /**
     * Метод для отправки сообщений.
     * Метод принимает два аргумента - chatId (для идентификации собеседника) и textToSend (текст сообщения)
     *
     * @param chatId
     * @param textToSend
     */
    public void sendMessage(long chatId, String textToSend) {
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


    public void onUpdateReceived(Visitor update) {
    }
}