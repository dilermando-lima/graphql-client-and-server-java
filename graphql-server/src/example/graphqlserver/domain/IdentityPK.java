package example.graphqlserver.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class IdentityPK {

    private static final Random RAND = new Random();
    
    @Id
    @Column(name = "id", nullable = false)
    protected String id;

    @Column(name = "date_insert", nullable = false, updatable = false)
    protected LocalDateTime dateInsert;

    @Column(name = "date_last_update", nullable = false)
    protected LocalDateTime dateLastUpdate;

    @PrePersist
    public void onInsert(){
        id = shortUUID();
        dateInsert = LocalDateTime.now();
        dateLastUpdate =  LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        dateLastUpdate =  LocalDateTime.now();
    }

    private static String shortUUID(){

        var now = LocalDateTime.now();
        Integer date = Integer.parseInt(DateTimeFormatter.ofPattern("yyyyMMdd").format(now)) - 20211101;
        Integer time = Integer.parseInt(DateTimeFormatter.ofPattern("HHmmss").format(now));
        Integer nano =  Integer.parseInt(String.valueOf(now.getNano() + "000000").substring(0, 6));
        Integer salt = RAND.nextInt(10000);

        return Long.toString( date ,36) +
                Long.toString( time ,36)  +
                Long.toString( nano ,36) + "-" +
                Long.toString( salt ,36);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
