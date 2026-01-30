package bd.edu.seu.bdms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String UserId;
    private String name;
    private String email;
    private String phoneNumber;
    private BooldGroup bloodGroup;
    private String password;
    private Role role;
    private String status;
}
