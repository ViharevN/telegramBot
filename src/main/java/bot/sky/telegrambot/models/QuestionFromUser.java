package bot.sky.telegrambot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Entity(name = "questionFromUsers")
@Data
@NoArgsConstructor
public class QuestionFromUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;

    //поле для хранения имени пользователя, от которого поступил вопрос
    private String questioner;

    private Timestamp questionDate;

    private String question;

    //поле-индикатор, рассмотрен вопрос или нет
    //если пользователю дан ответ, то ставим false, по дефолту сразу true
    boolean isActive = true;


    @Override
    public String toString() {
        return questionDate + " - " + questioner + " - " + question +"\n";
    }
}

//Как это работает:
// - получение вопроса от пользователя:
//      в Меню есть команды (для обоих приютов) /dogs_volunteer и /cats_volunteer
//      при выборе этих команд загружается информация из файлов dogs_volunteer.txt или cats_volunteer.txt
//      прочитав что надо делать, пользователь вводит свой вопрос, и его надо обработать и сохранить в БД в таблице questionFromUsers
//      теперь у нас есть запись в БД с этим вопросом.
//      когда волонтер выбирает в своем Меню команду /view_questions, то ему отображается список актуальных вопросов
//      в каком виде должен быть этот список?
//      допустим, что список вопросов будет выглядеть так:
//      _дата_ - _имя_пользователя_1_ - _вопрос_.
//      _дата_ - _имя_пользователя_2_ - _вопрос_.
//      _дата_ - _имя_пользователя_3_ - _вопрос_.
//      ...
//      Переопределим метод toString(), чтобы сразу иметь удобный вид.
//      В этот список попадают только вопросы, у которых значение поля  isActive = true

// И надо продумать механизм "закрытия" вопросов, т.е. указание волонтером в этом поле значения false  !!
