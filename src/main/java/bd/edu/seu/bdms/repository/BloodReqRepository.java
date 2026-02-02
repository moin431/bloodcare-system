package bd.edu.seu.bdms.repository;

import bd.edu.seu.bdms.model.BloodRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodReqRepository extends MongoRepository<BloodRequest,String> {

    long countByStatus(String status);
    List<BloodRequest> findByUserId(String userId);


}
