package bd.edu.seu.bdms.controller;

import bd.edu.seu.bdms.service.BloodStockService;
import bd.edu.seu.bdms.service.DonationHistoryService;
import bd.edu.seu.bdms.service.DonorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final DonorService donorService;
    private final BloodStockService bloodStockService;
    private final DonationHistoryService donationHistoryService;

    public HomeController(DonorService donorService, BloodStockService bloodStockService, DonationHistoryService donationHistoryService) {
        this.donorService = donorService;
        this.bloodStockService = bloodStockService;
        this.donationHistoryService = donationHistoryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        long totalDonors = donorService.getTotalDonors();
        long totalUnits = bloodStockService.getTotalBloodUnits();
        long livesSaved = totalUnits * 3;

        model.addAttribute("totalDonors", totalDonors);
        model.addAttribute("totalUnits", totalUnits);
        model.addAttribute("livesSaved", livesSaved);
        model.addAttribute("hospitals", 18);

        return "home";
    }
}
