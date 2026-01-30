package bd.edu.seu.bdms.repository;

import bd.edu.seu.bdms.model.DonationHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationHistoryRepository extends MongoRepository<DonationHistory,String> {
    List<DonationHistory> findByDonorId(String donorId);

}
