package bd.edu.seu.bdms.repository;

import bd.edu.seu.bdms.model.BloodStock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodStockRepository extends MongoRepository<BloodStock,String> {

    BloodStock findByBloodGroup(String bloodGroup);
}
