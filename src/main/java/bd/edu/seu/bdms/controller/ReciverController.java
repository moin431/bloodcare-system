package bd.edu.seu.bdms.controller;

import bd.edu.seu.bdms.model.BloodRequest;
import bd.edu.seu.bdms.model.Donor;
import bd.edu.seu.bdms.service.BloodReqService;
import bd.edu.seu.bdms.service.DonorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ReciverController {
    private final BloodReqService bloodReqService;
    private final DonorService donorService;

    public ReciverController(BloodReqService bloodReqService, DonorService donorService) {
        this.bloodReqService = bloodReqService;
        this.donorService = donorService;
    }

    @GetMapping("receiver-dashboard")
    public String receiverDashboard() {
        return "receiver-dashboard";
    }

    @GetMapping("blood-request")
    public String requestBlood(Model model) {
        model.addAttribute("request", new BloodRequest());
        return "blood-request";
    }

    @PostMapping("blood-request")
    public String getBloodRequest(@ModelAttribute BloodRequest request, Model model) {
        bloodReqService.saveRequest(request);

        List<Donor> matchedDonors = donorService.getMatchedDonors(request.getBloodGroup(), request.getHospitalLocation());

        model.addAttribute("donors", matchedDonors);
        model.addAttribute("group", request.getBloodGroup());

        return "matched-donors";
    }

    @GetMapping("req-status")
    public String requestStatus(Model model) {
        List<BloodRequest> bloodRequests = bloodReqService.getAllRequests();
        model.addAttribute("Requests", bloodRequests);
        return "req-status";
    }
}