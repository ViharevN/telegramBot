package bot.sky.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegramBotApplication {

    public static void main(String[] args) {

        System.out.println("Hello!");
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}
