package example.graphqlserver.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import example.graphqllibquery.builder.QueryBuilder;
import example.graphqlserver.domain.ContactEntity;
import example.graphqlserver.dto.ContactFilter;
import example.graphqlserver.repository.ContactRepository;
import graphql.schema.DataFetchingEnvironment;

@Service
public class ContactService {

    static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;

    @PersistenceContext
    private EntityManager entityManager;
    
    public ContactEntity getById(String id){
        return contactRepository.findById(id).orElse(null);
    }

    @Transactional
    public ContactEntity create(ContactEntity contactEntity){
        return contactRepository.save(contactEntity);
    }

    @Transactional
    public ContactEntity alter(ContactEntity contactEntity){
        return contactRepository.save(contactEntity);
    }

    public List<ContactEntity> list(ContactFilter contactEntity, DataFetchingEnvironment dataFetchingEnvironment){
        LOGGER.debug("Calling list() = list = {} " , contactEntity);

        return QueryBuilder.query(entityManager, "select obj from ContactEntity obj",ContactEntity.class).getResultList();
    }

}
