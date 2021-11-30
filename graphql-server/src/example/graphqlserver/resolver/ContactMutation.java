package example.graphqlserver.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.graphqlserver.domain.ContactEntity;
import example.graphqlserver.service.ContactService;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class ContactMutation implements GraphQLMutationResolver {
 
    static final Logger LOGGER = LoggerFactory.getLogger(ContactMutation.class);

    @Autowired
    private ContactService contactService;
        
    public ContactEntity createContact(ContactEntity contactEntity){
        return contactService.create(contactEntity);
    }

    public ContactEntity alterContact(ContactEntity contactEntity){
        return contactService.alter(contactEntity);
    }


}
