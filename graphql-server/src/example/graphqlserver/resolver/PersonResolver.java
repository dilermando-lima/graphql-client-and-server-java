package example.graphqlserver.resolver;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import example.model.Person;
import graphql.kickstart.tools.GraphQLQueryResolver;


@Component
public class PersonResolver implements GraphQLQueryResolver {

    static final Logger LOGGER = LoggerFactory.getLogger(PersonResolver.class);

    public Person getPersonById(Long id) {

        LOGGER.debug("Calling getPersonById() : id = {}", id);
        return new Person(id, "name " + id, LocalDate.now());
    }

    public List<Person> listPerson(){
        LOGGER.debug("Calling listPerson()");

        return List.of(
                new Person(1L, "name 1", LocalDate.now()),
                new Person(2L, "name 2", LocalDate.now()),
                new Person(2L, "name 3", LocalDate.now()),
                new Person(2L, "name 4", LocalDate.now()));
    }
}
