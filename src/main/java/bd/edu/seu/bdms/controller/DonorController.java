package bd.edu.seu.bdms.controller;

import bd.edu.seu.bdms.model.DonationHistory;
import bd.edu.seu.bdms.model.Donor;
import bd.edu.seu.bdms.model.User;
import bd.edu.seu.bdms.service.BloodStockService;
import bd.edu.seu.bdms.service.DonationHistoryService;
import bd.edu.seu.bdms.service.DonorService;
import bd.edu.seu.bdms.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/donor")
public class DonorController {

    private final UserService userService;
    private final DonationHistoryService donationHistoryService;
    private final DonorService donorService;
    private final BloodStockService bloodStockService;

    public DonorController(UserService userService, DonationHistoryService donationHistoryService, DonorService donorService, BloodStockService bloodStockService) {
        this.userService = userService;
        this.donationHistoryService = donationHistoryService;
        this.donorService = donorService;
        this.bloodStockService = bloodStockService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {

        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        List<DonationHistory> histories =
                donationHistoryService.findByDonorId(user.getId());

        int totalDonations = histories.size();

        LocalDate lastDonation = null;
        LocalDate nextEligible = null;
        boolean eligible = true;

        if(!histories.isEmpty()){
            lastDonation = histories.get(histories.size()-1).getDate();
            nextEligible = lastDonation.plusDays(90);

            eligible = LocalDate.now().isAfter(nextEligible)
                    || LocalDate.now().isEqual(nextEligible);
        }

        model.addAttribute("name", user.getName());
        model.addAttribute("lastDonation", lastDonation);
        model.addAttribute("nextEligible", nextEligible);
        model.addAttribute("totalDonations", totalDonations);
        model.addAttribute("eligible", eligible);

        model.addAttribute("name", user.getName());

        return "donor/donor-dashboard";
    }


    @GetMapping("/donate-history")
    public String donateHistory(Model model) {

        List<DonationHistory> list = donationHistoryService.findAll();
        model.addAttribute("donations", list);

        return "donor/donate-history";
    }

    @GetMapping("/availability")
    public String availability() {
        return "donor/availability";
    }

    @PostMapping("/donate")
    public String donate(Authentication authentication) {

        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        user.setTotalDonations(user.getTotalDonations() + 1);

        donorService.donate(user.getId(), user.getBloodGroup().name());
        bloodStockService.saveStock(user.getBloodGroup().name(), 1);

        userService.updateUser(user); // updates badge also

        return "redirect:/donor/dashboard?donated=true";
    }
    @GetMapping("/profile")
    public String profile(Model model, Authentication auth){

        User user = userService.findUserByEmail(auth.getName());
        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "donor/profile";
    }


    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user){

        userService.updateUser(user);

        return "redirect:/donor/profile?updated=true";
    }
}
