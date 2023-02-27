package bot.sky.telegrambot.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dogs")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "dogName")
    private String name;
    @Column(name = "dogBreed")
    private String breed;
    @Column(name = "dogAge")
    private int age;

    public Dog() {
    }

    public Dog(String name, String breed, int age) {
        this.name = name;
        this.breed = breed;
        this.age = age;
    }
}
