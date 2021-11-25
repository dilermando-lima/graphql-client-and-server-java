package example.graphqlclient;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import example.graphlibclient.AbstractGraphQlClient;
import example.model.Person;

@Component
public class GraphPersonClient extends AbstractGraphQlClient {


    static final Logger LOGGER = LoggerFactory.getLogger(GraphPersonClient.class);

    private static final String COMMAND_QUERY_PERSON_BY_ID = "getPersonById";
    private static final String COMMAND_QUERY_PERSON_LIST = "listPerson";

    public GraphPersonClient( @Value("${graphqlserver.uri}") String urlGraphQlServer ) {
        super(urlGraphQlServer);
    }
    

    public Person getPersonById(Long id){
        LOGGER.debug("Calling getPersonById() : id = {}", id);

        return this.query(COMMAND_QUERY_PERSON_BY_ID, 
                   String.format("id: %s",id), 
                   "id name", 
                    new ParameterizedTypeReference<ResponseBody<Person>>(){}
                   );
    }

    public List<Person> listPerson(){
        LOGGER.debug("Calling listPerson()");

        return this.query(COMMAND_QUERY_PERSON_LIST, 
                   "id name", 
                    new ParameterizedTypeReference<ResponseBody<List<Person>>>(){}
                   );
    }

}
