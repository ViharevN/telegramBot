package bot.sky.telegrambot.service;

import bot.sky.telegrambot.bot.TelegramBot;
import bot.sky.telegrambot.bot.config.BotConfig;
import bot.sky.telegrambot.bot.service.BotMenuCreator;

import bot.sky.telegrambot.models.Visitor;
import bot.sky.telegrambot.repository.CatShelterUsersRepository;
import bot.sky.telegrambot.repository.DogShelterUsersRepository;
import bot.sky.telegrambot.repository.VisitorsRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Class for testing TelegramBot
 * @see TelegramBot
 * @see VisitorsRepository
 * @author Kuzminykh Alexey
 */
@ExtendWith(MockitoExtension.class)
public class TelegramBotTest {

    @Mock
    private VisitorsRepository visitorsRepository;

    @InjectMocks
    private TelegramBot telegramBot;

    /**
     * Test for method <b>onUpdateReceived()</b> in TelegramBot
     * <br>
     *
     */
    @Test
    public void onUpdateReceivedTest() {
        //Инициализируем тестовые данные
        Visitor update = new Visitor();
        Message message = new Message();
        Visitor.setChatId(1234567L);
        message.setText("/start");
        update.setMessage(message);

        //Создаем mock объекты для зависимостей
        BotConfig botConfig = mock(BotConfig.class);
        VisitorsRepository visitorsRepository = mock(VisitorsRepository.class);
        DogShelterUsersRepository dogShelterUsersRepository = mock(DogShelterUsersRepository.class);
        CatShelterUsersRepository catShelterUsersRepository = mock(CatShelterUsersRepository.class);
        BotMenuCreator botMenuCreator = mock(BotMenuCreator.class);

        //Создаем объект для проверки
        TelegramBot telegramBot = new TelegramBot(botConfig, visitorsRepository, dogShelterUsersRepository,
                                        catShelterUsersRepository, botMenuCreator);

        //Устанавливаем поведение для зависимостей
        when(visitorsRepository.existsById(message.getChatId())).thenReturn(true);

        //Вызываем метод для проверки
        telegramBot.onUpdateReceived(update);

        //Проверяем правильность работы метода
        verify(visitorsRepository).existsById(message.getChatId());
        //Должен был быть вызван метод для регестрации посетителя
        verify(visitorsRepository).save(any());
        assertTrue(visitorsRepository.existsById(message.getChatId()));
    }

    /**
     * Test for method <b>registerVisitor()</b> in TelegramBot
     * <br>
     *
     */
    // Тест проверяет метод registerVisitor класса TelegramBot.
    // Метод принимает объект update и в зависимости от его параметров
    // создает нового посетителя и добавляет в базу данных.
    @Test
    public void registerVisitorTest() {
        // Создаем объект update для передачи в метод.
        Update update = new Update();

        // Данные для добавления пользователя в базу
        String name = "TestVisitor";
        long chatId = (long) 1;

        // Добавляем данные в объект update
        Message message = new Message();
        Chat chat = new Chat();
        chat.setFirstName(name);
        message.setChat(chat);
        Visitor.setChatId(chatId);
        update.setMessage(message);

        // Вызываем метод
        Visitor visitor = telegramBot.registerVisitor(update);

        // Проверяем результат
        assertEquals(name, visitor.getNameInChat());
        assertEquals(Optional.of(chatId), visitor.getChatId());
        assertEquals("", visitor.getVisitedShelter());

        Optional<Visitor> savedVisitor = visitorsRepository.findById(visitor.getChatId());
        assertTrue(savedVisitor.isPresent());
        assertEquals(name, savedVisitor.get().getNameInChat());
        assertEquals(Optional.of(chatId), savedVisitor.get().getChatId());
        assertEquals("", savedVisitor.get().getVisitedShelter());
    }

    /**
     * Test for method <b>registerUser()</b> in TelegramBot
     * <br>
     *
     */
    //Тестируем метод registerUser класса TelegramBot.
    //Метод принимает два параметра - текст и экземпляр класса Visitor.
    //Метод проверяет наличие и правильность введенных данных для регистрации пользователя.
    //В зависимости от посещенного раздела меню сохраняет в базу данных пользователя в соответствующую таблицу.
    //Возвращает true в случае успешной регистрации, и false в случае неправильно введенных данных.
    @Test
    void registerUserTest() {
        //Создаем экземпляр класса Visitor
        Visitor visitor = new Visitor(1L, "TestName", "/dog_shelter", "", null);
        //Создаем тестовые данные для регистрации
        String testInputText = "рег: TestName; TestLastName; +79998887766; test@mail.ru";
        //Вызываем метод registerUser
        boolean result = telegramBot.registerUser(testInputText, visitor);
        //Ожидаем true в случае успешной регистрации
        assertTrue(result);
    }

    /**
     * Test for method <b>sendMessage()</b> in TelegramBot
     * <br>
     *
     */
    @Test
    void sendMessageTest() {
        //Данный тест проверяет, что метод sendMessage правильно создает и отправляет сообщение
        //Создаем заглушку для отправки сообщения
        SendMessage messageStub = mock(SendMessage.class);
        //Создаем заглушку для метода execute
        try {
            when(telegramBot.execute(messageStub)).thenReturn(null);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        //Задаем chatId и текст сообщения
        long chatId = 123L;
        String textToSend = "Hello!";
        //Вызываем метод sendMessage
        telegramBot.sendMessage(chatId, textToSend);
        //Проверяем, что метод execute был вызван с правильными параметрами
        try {
            verify(telegramBot).execute(messageStub);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        //Проверяем, что методу setChatId передавался правильный параметр
        verify(messageStub).setChatId(chatId);
        //Проверяем, что методу setText передавался правильный параметр
        verify(messageStub).setText(textToSend);
    }
}









