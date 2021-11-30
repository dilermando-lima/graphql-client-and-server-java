package example.graphqlserver.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import example.graphqlserver.domain.PersonEntity;
import example.graphqlserver.dto.PersonFilter;
import example.graphqlserver.repository.PersonRepository;

@Service
public class PersonService {

    static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;
    
    public PersonEntity getById(String id){
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public PersonEntity create(PersonEntity personEntity){
        return personRepository.save(personEntity);
    }

    @Transactional
    public PersonEntity alter(PersonEntity personEntity){
        return personRepository.save(personEntity);
    }

    public List<PersonEntity> list(PersonFilter personFilter){
        LOGGER.debug("Calling list() = personFilter = {} " , personFilter);
        return personRepository.findAll();
    }

}
