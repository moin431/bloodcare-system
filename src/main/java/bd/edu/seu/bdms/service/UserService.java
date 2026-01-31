package bd.edu.seu.bdms.service;

import bd.edu.seu.bdms.model.User;
import bd.edu.seu.bdms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        user.setStatus("ACTIVE");
        userRepository.save(user);
    }

    public User login(String email, String password){


        Optional<User> optionalUser =
                userRepository.findByEmailIgnoreCase(email.trim());

        if(optionalUser.isEmpty()){
            return null;
        }

        User user = optionalUser.get();

        if(user.getPassword().equals(password)){
            return user;
        }

        return null;
    }


    public User findUserByEmail(String email){
        return userRepository.findByEmailIgnoreCase(email.trim()).orElse(null);
    }
    public long countByRole(String role){
        return userRepository.countByRole(role);
    }

    public List<User> findAllDonors(){
        return userRepository.findByRole("DONOR");
    }

    public void assignBadge(User user){

        int donations = user.getTotalDonations();

        if(donations >= 20){
            user.setBadge("GOLD");
        }
        else if(donations >= 10){
            user.setBadge("SILVER");
        }
        else{
            user.setBadge("BRONZE");
        }
    }


    public void updateUser(User user) {
        assignBadge(user);

        userRepository.save(user);
    }
}
