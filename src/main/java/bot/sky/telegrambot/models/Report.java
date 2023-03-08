package bot.sky.telegrambot.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "files")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Имя пользователя
    @Column(name = "user_name")
    private String userName;
    //id пользователя
    @Column(name = "user_id")
    private String userId;
    //url файла
    @Column(name = "url")
    private String fileUrl;
    //сам отчет
    @Column(name = "description")
    private String descriptionReport;

}
