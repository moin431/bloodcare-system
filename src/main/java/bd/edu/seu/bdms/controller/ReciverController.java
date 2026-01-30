package bd.edu.seu.bdms.controller;

import bd.edu.seu.bdms.model.BloodRequest;
import bd.edu.seu.bdms.service.BloodReqService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ReciverController {
    private final BloodReqService bloodReqService;

    public ReciverController(BloodReqService bloodReqService) {
        this.bloodReqService = bloodReqService;
    }

    @GetMapping("receiver-dashboard")
    public String receiverDashboard(){
        return "receiver-dashboard";
    }
    @GetMapping("blood-request")
    public String requestBlood(Model model){
        model.addAttribute("request",new BloodRequest());
        return "blood-request";
    }
    @PostMapping("blood-request")
    public String getBloodRequest(@ModelAttribute BloodRequest request){
        bloodReqService.saveRequest(request);
        return "redirect:/blood-request";
    }
    @GetMapping("req-status")
    public String requestStatus(Model model){
        List<BloodRequest> bloodRequests = bloodReqService.getAllRequests();
        model.addAttribute("Requests",bloodRequests);
        return "req-status";
    }
}
