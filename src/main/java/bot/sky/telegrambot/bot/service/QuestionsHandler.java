package bot.sky.telegrambot.bot.service;

import bot.sky.telegrambot.models.QuestionFromUser;
import bot.sky.telegrambot.repository.QuestionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class QuestionsHandler {
    final private QuestionsRepository questionsRepository;

    public QuestionsHandler(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public String getAllActiveQuestions(){
        List<QuestionFromUser> listOfQuestions;
        String resultListForView = "";
        listOfQuestions = (List<QuestionFromUser>) questionsRepository.findAll();
        for (QuestionFromUser question: listOfQuestions) {
            if (question.isActive()){
                resultListForView = resultListForView + question;
            }
        }
        return resultListForView;
    }
}
