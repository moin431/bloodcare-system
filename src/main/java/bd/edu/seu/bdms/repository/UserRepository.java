package bd.edu.seu.bdms.repository;

import bd.edu.seu.bdms.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByEmailIgnoreCase(String email);
    long countByRole(String role);
    List<User> findByRole(String role);
}
