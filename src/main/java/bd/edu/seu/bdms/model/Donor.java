package bd.edu.seu.bdms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Donor {
    @Id
    private String id;
    private String userId;
    private String bloodGroup;
    private boolean eligible;
    private LocalDate lastDonationDate;
}
