package example.model;

import java.time.LocalDate;

public class Person {
    
    private Long id;
    private String name;
    private LocalDate dateBirth;

    public Person() {
    }

    public Person(Long id, String name, LocalDate dateBirth) {
        this.id = id;
        this.name = name;
        this.dateBirth = dateBirth;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    
}
