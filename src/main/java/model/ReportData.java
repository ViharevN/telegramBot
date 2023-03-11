package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ReportData {
    @Id
    @GeneratedValue
    private long id;
    private Long chatId;
    private String ration;
    private String health;
    private String habits;
    private long days;
}
