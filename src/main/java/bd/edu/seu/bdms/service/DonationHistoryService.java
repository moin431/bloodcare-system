package bd.edu.seu.bdms.service;

import bd.edu.seu.bdms.model.DonationHistory;
import bd.edu.seu.bdms.repository.DonationHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationHistoryService {
    private final DonationHistoryRepository donationHistoryRepository;

    public DonationHistoryService(DonationHistoryRepository donationHistoryRepository) {
        this.donationHistoryRepository = donationHistoryRepository;
    }
    public List<DonationHistory> findAll(){
        return donationHistoryRepository.findAll();
    }
    public List<DonationHistory> findByDonorId(String donorId){
        return donationHistoryRepository.findByDonorId(donorId);
    }
}
