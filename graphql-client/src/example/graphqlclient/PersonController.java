package example.graphqlclient;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import example.model.Person;

@Controller
@RequestMapping("/person")
public class PersonController {

    static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

     @Autowired
    private GraphPersonClient graphPersonClient;

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id")  Long id){
        LOGGER.debug("Calling GET getPersonById() : id = {}", id);
        
        return ResponseEntity.ok(graphPersonClient.getPersonById(id));
    }

    @GetMapping
    public ResponseEntity<List<Person>> listPerson(){
        LOGGER.debug("Calling GET listPerson()");
        return ResponseEntity.ok(graphPersonClient.listPerson());
    }

}
