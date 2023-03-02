package bot.sky.telegrambot.bot;

import bot.sky.telegrambot.models.*;
import bot.sky.telegrambot.repository.CatShelterUsersRepository;
import bot.sky.telegrambot.repository.DogShelterUsersRepository;
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
import bot.sky.telegrambot.repository.VisitorsRepository;


/**
 * Один из основных классов приложения - класс следит за обновлением строки чата,
 * и, если пользователь что-то ввел, то обрабатывает полученные данные.
 * Класс содержит приватные методы по созданию экземпляров классов Visitor и
 * осуществляет регистрацию пользователей приюта для собак и приюта для кошек.
 *
 * @author Мухаметзянов Эдуард
 */

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
                       BotMenuCreator botMenuCreator, CommandSelector commandSelector,
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
                    botMenuCreator.addFirstCommandsToBotMenu(),
                    new BotCommandScopeDefault(),
                    null));
        } catch (TelegramApiException e) {
            log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
        }
    }

    //Этот метод надо глубоко переработать!
    @Override
    public void onUpdateReceived(Update update) {
        //Получаем  chatId - уникальный идентификатор пользователя
        //т.е. узнаем кто нам написал
        Long chatId = update.getMessage().getChatId();
        //Ищем в БД такого пользователя, и уже исходя из его статуса строится дальнейшее общение
        //И обновляем Меню для пользователя, соответствующее его статусу
        //Если пользователь уже есть в БД, то меняем ему отображаемое Меню
        if (testUsersRepository.existsById(chatId)) {
            //Для удобства создадим пользователя
            TestUserForMenu testUserForMenu = testUsersRepository.findById(chatId).get();
            //Узнаем его внутренни статус и заполняем Меню под него
            if (testUserForMenu.getInnerStatusUser() == InnerStatusUser.NOT_REGISTERED_USER
                    && testUserForMenu.getVisitedShelter().equals("/dog_shelter")) {
                try {
                    execute(new SetMyCommands(
                            botMenuCreator.addDogShelterUsersCommandsToBotMenu(),
                            new BotCommandScopeDefault(),
                            null));
                } catch (TelegramApiException e) {
                    log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
                }
            }
            if (testUserForMenu.getInnerStatusUser() == InnerStatusUser.NOT_REGISTERED_USER
                    && testUserForMenu.getVisitedShelter().equals("/cat_shelter")) {
                try {
                    execute(new SetMyCommands(
                            botMenuCreator.addCatShelterUsersCommandsToBotMenu(),
                            new BotCommandScopeDefault(),
                            null));
                } catch (TelegramApiException e) {
                    log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
                }
            }

            if (testUserForMenu.getInnerStatusUser() == InnerStatusUser.REGISTERED_USER
                    && testUserForMenu.getVisitedShelter().equals("/dog_shelter")) {
                try {
                    execute(new SetMyCommands(
                            botMenuCreator.addDogShelterUsersCommandsToBotMenu(),
                            new BotCommandScopeDefault(),
                            null));
                } catch (TelegramApiException e) {
                    log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
                }
            }

            if (testUserForMenu.getInnerStatusUser() == InnerStatusUser.REGISTERED_USER
                    && testUserForMenu.getVisitedShelter().equals("/cat_shelter")) {
                try {
                    execute(new SetMyCommands(
                            botMenuCreator.addCatShelterUsersCommandsToBotMenu(),
                            new BotCommandScopeDefault(),
                            null));
                } catch (TelegramApiException e) {
                    log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
                }
            }
            if (testUserForMenu.getInnerStatusUser() == InnerStatusUser.USER_WITH_PET
                    && testUserForMenu.getVisitedShelter().equals("/dog_shelter")) {
                try {
                    execute(new SetMyCommands(
                            botMenuCreator.addCommandsToBotMenuForRegisteredUsersAfterAdoptDog(),
                            new BotCommandScopeDefault(),
                            null));
                } catch (TelegramApiException e) {
                    log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
                }
            }
            if (testUserForMenu.getInnerStatusUser() == InnerStatusUser.USER_WITH_PET
                    && testUserForMenu.getVisitedShelter().equals("/cat_shelter")) {
                try {
                    execute(new SetMyCommands(
                            botMenuCreator.addCommandsToBotMenuForRegisteredUsersAfterAdoptCat(),
                            new BotCommandScopeDefault(),
                            null));
                } catch (TelegramApiException e) {
                    log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
                }
            }
        }
        //А если такого пользователя нет, то выводим приветствие и проводим первичную (скрытую) регистрацию
        else {
            String nameInChat = update.getMessage().getChat().getFirstName();
            String textToSend = "Здравствуйте " + nameInChat + "!\n\n" +
                    "Пожалуйста, выберите команду /start в Menu";
            silentRegistration(chatId, textToSend);
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

            //Здесь нужен вызов метода регистрации пользователя - ДОДЕЛАТЬ !
        }
        //Если текст начинается с секретного кода ("/dgrgrhtr"), то это заходит в чат волонтер
        //И для него надо обновить волонтерское меню
        if (inputText.startsWith("/dgrgrhtr")) {
            try {
                execute(new SetMyCommands(
                        botMenuCreator.addCommandsToBotMenuForVolunteer(),
                        new BotCommandScopeDefault(),
                        null));
            } catch (TelegramApiException e) {
                log.error("Ошибка формирования МЕНЮ бота: " + e.getMessage());
            }
        }
        //Здесь же надо прописать обработку полученной фотографии питомца, которая является частью отчета

        //Если текст начинается с "отчет:", то значит пользователь добавляет отчетные данные
        //Надо продумать как маркировать команды, которые связаны с отправкой отчета !!!!

    }

    private void silentRegistration(Long chatId, String nameInChat) {
        TestUserForMenu testUserForMenu = new TestUserForMenu();
        testUserForMenu.setChatId(chatId);
        testUserForMenu.setNameInChat(nameInChat);
        testUsersRepository.save(testUserForMenu);
        log.info("Новый пользователь " + nameInChat + " записан в БД");
    }

    /**
     * Метод для авто-регистрации посетителей чата.
     * Сохраняется chatId и имя посетителя.
     *
     * @param update
     * @author Мухаметзянов Эдуард
     */

    // Скорее всего просто УДАЛИТЬ этот метод !!!
    private Visitor registerVisitor(Update update) {
        var chatId = update.getMessage().getChatId();
        var name = update.getMessage().getChat().getFirstName();
        Visitor visitor = new Visitor();
        visitor.setNameInChat(name);
        visitor.setChatId(chatId);
        visitor.setVisitedShelter("");
        visitorsRepository.save(visitor);
        log.info("Посетитель " + name + " добавлен в БД посетителей");
        return visitor;
    }

    /**
     * Метод для регистрации пользователей, которые планируют взять питомца из приюта.
     * По итогам работы метода может быть создан один из двух зарегистрированных пользователей
     * (для каждого приюта) или не создан, если введены некорректные регистрационные данные.
     *
     * @author Мухаметзянов Эдуард
     */

    // !!!! ПЕРЕДЕЛАТЬ МЕТОД !!!!
    private boolean registerUser(String inputText, Visitor visitor) {
        //Зарегистрированный пользователь раньше был просто посетителем,
        //Значит часть информации можно получить из экземпляра visitor
        //Соберем значения полей, чтобы потом их присвоить
        Long newChatId = visitor.getChatId();
        String newNameInChat = visitor.getNameInChat();
        String visitedShelter = visitor.getVisitedShelter();
        //Получим значения полей из распарсенной строки
        String text = inputText.replaceFirst("рег:", "");
        System.out.println(inputText + " --> " + text);
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
        String selectedShelter;
        //Значения всех будущих полей получены, но надо решить в качестве кого регистрируется пользователь?
        //И тут мы учитываем из какого раздела меню он перешел в регистрацию.
        //Если из раздела "приют для собак", то в туда его и регистрируем, и наоборот.
        if (!visitorsRepository.findById(visitor.getChatId()).get().getVisitedShelter().isEmpty()) {
            selectedShelter = visitorsRepository.findById(visitor.getChatId()).get().getVisitedShelter();
            if (selectedShelter.equals("/dog_shelter")) {
                RegisteredUserForDogShelter newUserForDogShelter = new RegisteredUserForDogShelter();
                newUserForDogShelter.setChatId(newChatId);
                newUserForDogShelter.setNameInChat(newNameInChat);
                newUserForDogShelter.setFirstName(newFirstName);
                newUserForDogShelter.setLastName(newLastName);
                newUserForDogShelter.setPhoneNumber(newPhoneNumber);
                newUserForDogShelter.setE_mail(newEmail);
                newUserForDogShelter.setVisitedShelter(selectedShelter);
                dogShelterUsersRepository.save(newUserForDogShelter);
            }
            if (selectedShelter.equals("/cat_shelter")) {
                RegisteredUserForCatShelter newUserFoCatShelter = new RegisteredUserForCatShelter();
                newUserFoCatShelter.setChatId(newChatId);
                newUserFoCatShelter.setNameInChat(newNameInChat);
                newUserFoCatShelter.setFirstName(newFirstName);
                newUserFoCatShelter.setLastName(newLastName);
                newUserFoCatShelter.setPhoneNumber(newPhoneNumber);
                newUserFoCatShelter.setE_mail(newEmail);
                newUserFoCatShelter.setVisitedShelter(selectedShelter);
                catShelterUsersRepository.save(newUserFoCatShelter);
            }
            return true;
        }
        return false;
    }


    /**
     * Метод для отправки сообщений.
     * Метод принимает два аргумента - chatId (для идентификации собеседника) и textToSend (текст сообщения)
     *
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