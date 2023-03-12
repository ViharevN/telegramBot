package bot.sky.telegrambot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class PersonDog {

    /** "ID" field */
    @Id
    @GeneratedValue
    private Long id;

    /** "Name" field */
    private String name;

    /** "Year Of Birth" field */
    private int yearOfBirth;

    /** "Phone" field */
    private String phone;

    /** "Mail" field */
    private String mail;

    /** "Address" field */
    private String address;

    /** "id Chat" field */
    private Long chatId;

    private Status status;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @OneToOne(orphanRemoval = true)
    @JoinTable(name = "person_report_data",
            joinColumns = @JoinColumn(name = "person_null"),
            inverseJoinColumns = @JoinColumn(name = "report_data_id"))
    private ReportData reportData;


    /**
     * Constructor - creating a new object with certain values.
     * @param name
     * @param phone
     * @param chatId
     */
    public PersonDog(String name, String phone, Long chatId) {
        this.name = name;
        this.phone = phone;
        this.chatId = chatId;
    }

    /**
     * Constructor - creating a new object with certain values.
     * @param id
     * @param name
     * @param yearOfBirth
     * @param phone
     * @param mail
     * @param address
     * @param chatId
     */
    public PersonDog(Long id, String name, int yearOfBirth, String phone, String mail, String address, Long chatId) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.chatId = chatId;
    }
}
