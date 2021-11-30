package example.graphqlserver.resolver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.graphqlserver.domain.ContactEntity;
import example.graphqlserver.dto.ContactFilter;
import example.graphqlserver.service.ContactService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;


@Component
public class ContactQuery implements GraphQLQueryResolver {

    static final Logger LOGGER = LoggerFactory.getLogger(ContactQuery.class);

    @Autowired
    private ContactService contactService;

    public ContactEntity getContactById(String id){
        return contactService.getById(id);
    }
        
    
    public List<ContactEntity> listContact(ContactFilter contactFilter, DataFetchingEnvironment dataFetchingEnvironment){
        return contactService.list(contactFilter, dataFetchingEnvironment);
    }
}
