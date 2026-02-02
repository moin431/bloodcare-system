package bd.edu.seu.bdms.repository;

import bd.edu.seu.bdms.model.Donor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonorRepositry extends MongoRepository<Donor,String> {
    Donor findByUserId(String userId);
    List<Donor> findByBloodGroupAndCityAndEligibleTrue(String bloodGroup, String city);
    List<Donor> findByBloodGroupAndEligibleTrue(String bloodGroup);

}
