package bd.edu.seu.bdms.controller;

import bd.edu.seu.bdms.model.Donor;
import bd.edu.seu.bdms.model.Role;
import bd.edu.seu.bdms.model.User;
import bd.edu.seu.bdms.service.DonorService;
import bd.edu.seu.bdms.service.JwtService;
import bd.edu.seu.bdms.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class AuthController {
    private final UserService userService;
    private final DonorService donorService;
    private final JwtService jwtService;

    public AuthController(UserService userService, DonorService donorService, JwtService jwtService) {
        this.userService = userService;
        this.donorService = donorService;
        this.jwtService = jwtService;
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @PostMapping("login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpServletResponse response,
                        Model model){

        User user = userService.login(email,password);

        if(user == null){
            model.addAttribute("error","Wrong email or password");
            return "login";
        }


        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return "redirect:/redirect";
    }


    @GetMapping("/redirect")
    public String redirectByRole(Authentication authentication) {

        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            return "redirect:/login";
        }

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();


        if ("ROLE_ADMIN".equals(role)) {
            return "redirect:/admin-dashboard";
        }

        if ("ROLE_DONOR".equals(role)) {
            return "redirect:/donor/dashboard";
        }

        if ("ROLE_RECEIVER".equals(role)) {
            return "redirect:/receiver-dashboard";
        }

        return "redirect:/login";
    }


    @GetMapping("/logout")
    public String logout(HttpServletResponse response){

        Cookie cookie = new Cookie("JWT", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        SecurityContextHolder.clearContext();

        return "redirect:/login";
    }



    @GetMapping("registration")
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }
    @PostMapping("registration")
    public String doRegistration(@ModelAttribute User user){
        userService.saveUser(user);
        if(user.getRole().equals(Role.DONOR)){

            Donor donor = new Donor();
            donor.setUserId(user.getId());
            donor.setBloodGroup(user.getBloodGroup().toString());
            donor.setEligible(true);


            donorService.createDonor(donor);
        }

        return "redirect:/login";
    }
}
