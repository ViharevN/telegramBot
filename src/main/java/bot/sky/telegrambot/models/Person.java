package bot.sky.telegrambot.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name="description")
    private String description;
    @Column(name = "telegramID")
    private String telegramId;
    @Column(name = "roles")
    private UserRoles roles;

    public Person() {
    }

    public Person(String firstName, String lastName, String description,String telegramId, UserRoles roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.telegramId = telegramId;
        this.roles = roles;
    }
}
