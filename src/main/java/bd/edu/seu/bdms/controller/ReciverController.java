package bd.edu.seu.bdms.controller;

import bd.edu.seu.bdms.model.BloodRequest;
import bd.edu.seu.bdms.model.Donor;
import bd.edu.seu.bdms.model.User;
import bd.edu.seu.bdms.service.BloodReqService;
import bd.edu.seu.bdms.service.DonorService;
import bd.edu.seu.bdms.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ReciverController {
    private final BloodReqService bloodReqService;
    private final DonorService donorService;
    private final UserService userService;

    public ReciverController(BloodReqService bloodReqService, DonorService donorService, UserService userService) {
        this.bloodReqService = bloodReqService;
        this.donorService = donorService;
        this.userService = userService;
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
    public String getBloodRequest(@ModelAttribute BloodRequest request, Model model, Authentication auth) {
        User user = userService.findUserByEmail(auth.getName());

        request.setUserId(user.getId());
        bloodReqService.saveRequest(request);

        List<Donor> matchedDonors = donorService.getMatchedDonors(request.getBloodGroup(), request.getCity(), request.isEmergency());

        model.addAttribute("donors", matchedDonors);
        model.addAttribute("group", request.getBloodGroup());

        return "matched-donors";
    }

    @GetMapping("req-status")
    public String requestStatus(Model model, Authentication auth) {
        User user = userService.findUserByEmail(auth.getName());

        List<BloodRequest> bloodRequests = bloodReqService.getRequestsByUser(user.getId());

        model.addAttribute("Requests", bloodRequests);
        return "req-status";
    }
    @GetMapping("/start-chat/{donorId}")
    public String startChat(@PathVariable String donorId,
                            Authentication authentication){

        String receiverId = authentication.getName();
        String roomId = donorId + "_" + receiverId;

        return "redirect:/chat/" + roomId;
    }
}