package bot.sky.telegrambot.service;

import bot.sky.telegrambot.configuration.BotConfig;
import bot.sky.telegrambot.models.Report;
import bot.sky.telegrambot.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${service.file_info.uri}")
    private String fileInfoUri;
    @Autowired
    private ReportRepository repository;
    private BotConfig botConfig;

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //send mediaFiles
        Message message = update.getMessage();
        String chatId = message.getChatId().toString();
        //find out the type file
        if (update.hasMessage()) {
            //text message
            if (message.hasText()) {
                System.out.println("text");
                //photo message
            } else if (message.hasPhoto()) {
                PhotoSize photo = message.getPhoto().get(0);

                String fileId = photo.getFileId();
                ResponseEntity<String> response = getFilePath(fileId);
                System.out.println(response);
                System.out.println(response.getBody());
                System.out.println("download photo");
                //file message
            } else if (message.hasDocument()) {
                GetFile getFile = new GetFile(update.getMessage().getDocument().getFileId());
                try {
                    Report report = new Report();
                    File file = execute(getFile);
                    String fileId = file.getFileId();
                    String url = file.getFileUrl(botConfig.getBotToken());
                    String userName = update.getMessage().getFrom().getFirstName();
                    String userId = update.getMessage().getFrom().getId().toString();
                    String description = update.getMessage().getCaption();
                            report.setUserName(userName);
                            report.setFileUrl(url);
                            report.setUserId(userId);
                            report.setDescriptionReport(description);
                            repository.save(report);

                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("downloaded doc");
            } else if (message.hasVideo()) {
                System.out.println("downloaded video");
            } else if (message.hasVoice()) {
                System.out.println("send voice");
            } else if (message.hasAnimation()) {
                System.out.println("send sticker");
            } else if (message.hasAudio()) {
                System.out.println("send audio");
            } else if (message.hasLocation()) {
                System.out.println("location");
            } else if (message.hasContact()) {
                System.out.println("Contact");
            }
        } else if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage();
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void startCommandReceived(long chatId, String name) {

        String answer = "Hi, " + name + ", nice to meet you!";
        sendMessage(chatId, answer);

    }

    private ResponseEntity<String> getFilePath(String fileId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange(
                fileInfoUri,
                HttpMethod.GET,
                request,
                String.class,
                botConfig.getBotToken(),
                fileId
        );
    }

    


}
