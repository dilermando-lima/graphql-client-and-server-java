package example.graphqlserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import example.graphqlserver.domain.ContactEntity;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity,String>{
    
}
