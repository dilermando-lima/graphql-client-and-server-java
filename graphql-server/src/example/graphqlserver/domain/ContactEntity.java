package example.graphqlserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contact")
public class ContactEntity extends IdentityPK{

    @JoinColumn(name = "id_person", nullable = false, updatable = false)
    @ManyToOne
    private PersonEntity person;
    
    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    
}
