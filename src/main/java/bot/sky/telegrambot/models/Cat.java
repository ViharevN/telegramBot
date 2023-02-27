package bot.sky.telegrambot.models;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "catName")
    private String catName;
    @Column(name = "catBreed")
    private String breed;
    @Column(name = "catAge")
    private int age;

    public Cat() {
    }

    public Cat(String catName, String breed, int age) {
        this.catName = catName;
        this.breed = breed;
        this.age = age;
    }
}
