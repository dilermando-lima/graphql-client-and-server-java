package example.graphqlserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.graphqlserver.domain.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,String>{
    
}
