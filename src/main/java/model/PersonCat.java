package model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class PersonCat{


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
        @JoinColumn(name = "cat_id")
        private Cat cat;

        @OneToOne(orphanRemoval = true)
        @JoinTable(name = "person_cat_report_data",
                joinColumns = @JoinColumn(name = "person_cat_null"),
                inverseJoinColumns = @JoinColumn(name = "report_data_id"))
        private ReportData reportData;




    }

