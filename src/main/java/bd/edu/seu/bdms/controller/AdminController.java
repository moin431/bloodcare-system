package bd.edu.seu.bdms.controller;

import bd.edu.seu.bdms.model.BloodRequest;
import bd.edu.seu.bdms.model.BloodStock;
import bd.edu.seu.bdms.model.Donor;
import bd.edu.seu.bdms.model.User;
import bd.edu.seu.bdms.service.BloodReqService;
import bd.edu.seu.bdms.service.BloodStockService;
import bd.edu.seu.bdms.service.DonorService;
import bd.edu.seu.bdms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminController {
    private final BloodReqService bloodReqService;
    private final BloodStockService bloodStockService;
    private final DonorService donorService;
    private final UserService userService;

    public AdminController(BloodReqService bloodReqService, BloodStockService bloodStockService, DonorService donorService, UserService userService) {
        this.bloodReqService = bloodReqService;
        this.bloodStockService = bloodStockService;
        this.donorService = donorService;
        this.userService = userService;
    }


    @GetMapping("admin-dashboard")
    public String adminDashboard(Model model){
        List<BloodRequest> bloodRequestList = bloodReqService.getAllRequests();
        model.addAttribute("bloodRequests",bloodRequestList);

        long totalDonors = userService.countByRole("DONOR");
        long pendingRequests = bloodReqService.countByStatus("PENDING");
        int totalUnits = bloodStockService.getTotalUnits();
        List<User> donors = userService.findAllDonors();

        model.addAttribute("totalDonors", totalDonors);
        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("bloodUnits", totalUnits);
        model.addAttribute("donors", donors);

        return "admin-dashboard";
    }
    @GetMapping("stocks")
    public String stocks(Model model){

        List<BloodStock> bloodStocks = bloodStockService.findAll();
        model.addAttribute("stocks",bloodStocks);

        return "stocks";
    }
    @GetMapping("/stock/{group}")
    @ResponseBody
    public int getStock(@PathVariable String group){

        BloodStock stock = bloodStockService.findByGroup(group);

        if(stock != null)
            return stock.getUnits();
        else
            return 0;
    }

    @GetMapping("donors")
    public String donors(){

        return "donors";
    }
    @GetMapping("reports")
    public String reports(){
        return "reports";
    }
    @GetMapping("requests")
    public String requests(Model model){
        List<BloodRequest> bloodRequestList = bloodReqService.getAllRequests();
        model.addAttribute("bloodRequestList",bloodRequestList);
        return "requests";
    }
    @GetMapping("request/{id}/approve")
    public String approve(@PathVariable String id){
        bloodReqService.approveRequest(id);

        return "redirect:/requests";
    }
    @GetMapping("request/{id}/reject")
    public String reject(@PathVariable String id){
        bloodReqService.rejectRequest(id);

        return "redirect:/requests";
    }
}
