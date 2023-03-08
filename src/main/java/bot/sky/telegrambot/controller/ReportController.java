package bot.sky.telegrambot.controller;

import bot.sky.telegrambot.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
public class ReportController {
    @Autowired
    private TelegramBot telegramBot;


}
