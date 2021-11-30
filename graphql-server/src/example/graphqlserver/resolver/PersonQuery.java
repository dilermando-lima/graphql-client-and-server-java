package example.graphqlserver.resolver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.graphqlserver.domain.PersonEntity;
import example.graphqlserver.dto.PersonFilter;
import example.graphqlserver.service.PersonService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;


@Component
public class PersonQuery implements GraphQLQueryResolver {

    static final Logger LOGGER = LoggerFactory.getLogger(PersonQuery.class);

    @Autowired
    private PersonService personService;

    public PersonEntity getPersonById(String id){
        return personService.getById(id);
    }
        

    public List<PersonEntity> listPerson(PersonFilter personFilter, DataFetchingEnvironment dataFetchingEnvironment){

         System.out.println( dataFetchingEnvironment.getField().getSelectionSet().getSelections().toString());


        return personService.list(personFilter);
    }
}
