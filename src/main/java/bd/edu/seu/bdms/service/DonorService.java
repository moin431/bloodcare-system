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

    public long getTotalDonors() {
        return  donorRepositry.count();
    }

    public List<Donor> getMatchedDonors(String bloodGroup,String city) {
        return donorRepositry.findByBloodGroupAndCityAndEligibleTrue(bloodGroup,city);
    }
    public String calculateBadge(int donations) {

        if(donations >= 10) return "PLATINUM HERO";
        else if(donations >= 6) return "GOLD DONOR";
        else if(donations >= 3) return "SILVER DONOR";
        else if(donations >= 1) return "BRONZE DONOR";

        return "NEW DONOR";
    }
    public void updateDonation(Donor donor){

        int newCount = donor.getTotalDonations() + 1;
        donor.setTotalDonations(newCount);

        String badge = calculateBadge(newCount);
        donor.setBadge(badge);

        donorRepositry.save(donor);
    }
    public Donor getDonorByUserId(String userId){
        return donorRepositry.findByUserId(userId);
    }
    public List<Donor> getMatchedDonors(String group, String location, boolean emergency){

        if(emergency){
            return donorRepositry.findByBloodGroupAndEligibleTrue(group);
        }

        return donorRepositry.findByBloodGroupAndCityAndEligibleTrue(
                group, location
        );
    }


    public Donor findById(String donorId) {
        return donorRepositry.findById(donorId).orElse(null);
    }
}
