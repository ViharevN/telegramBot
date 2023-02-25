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
    @Column(name = "roles")
    private UserRoles roles;

    public Person() {
    }

    public Person(String firstName, String lastName, UserRoles roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }
}
