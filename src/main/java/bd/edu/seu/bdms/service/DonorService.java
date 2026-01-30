package bd.edu.seu.bdms.service;

import bd.edu.seu.bdms.model.DonationHistory;
import bd.edu.seu.bdms.model.Donor;
import bd.edu.seu.bdms.repository.DonationHistoryRepository;
import bd.edu.seu.bdms.repository.DonorRepositry;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DonorService {
    private final DonorRepositry donorRepositry;
    private final DonationHistoryRepository donationHistoryRepository;

    public DonorService(DonorRepositry donorRepositry, DonationHistoryRepository donationHistoryRepository) {
        this.donorRepositry = donorRepositry;
        this.donationHistoryRepository = donationHistoryRepository;
    }
    public Donor createDonor(Donor donor) {
        return donorRepositry.save(donor);
    }
    public void donate(String userId, String bloodGroup){

        Donor donor = donorRepositry.findByUserId(userId);

        donor.setLastDonationDate(LocalDate.now());
        donor.setEligible(false);

        donorRepositry.save(donor);

        DonationHistory history = new DonationHistory();
        history.setDonorId(userId);
        history.setBloodGroup(bloodGroup);
        history.setUnits(1);
        history.setStatus("COMPLETED");
        history.setDate(LocalDate.now());

        donationHistoryRepository.save(history);
    }
    public List<Donor> findAllDonors(){
        return donorRepositry.findAll();
    }

}
