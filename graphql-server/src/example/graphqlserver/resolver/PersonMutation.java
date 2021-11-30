package example.graphqlserver.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.graphqlserver.domain.PersonEntity;
import example.graphqlserver.service.PersonService;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class PersonMutation implements GraphQLMutationResolver {
 
    static final Logger LOGGER = LoggerFactory.getLogger(PersonMutation.class);

    @Autowired
    private PersonService personService;
        
    public PersonEntity createPerson(PersonEntity personEntity){
        return personService.create(personEntity);
    }

    public PersonEntity alterPerson(PersonEntity personEntity){
        return personService.alter(personEntity);
    }


}
