package example.graphqlclient.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Person {

    private String id;
    private String name;
    private LocalDate dateBirth;
    private LocalDateTime dateInsert;
    private LocalDateTime dateLastUpdate;

    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getDateBirth() {
        return dateBirth;
    }
    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }
    public LocalDateTime getDateInsert() {
        return dateInsert;
    }
    public void setDateInsert(LocalDateTime dateInsert) {
        this.dateInsert = dateInsert;
    }
    public LocalDateTime getDateLastUpdate() {
        return dateLastUpdate;
    }
    public void setDateLastUpdate(LocalDateTime dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    
    
}
