package example.graphqlserver.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class PersonEntity extends IdentityPK{
    
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

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
