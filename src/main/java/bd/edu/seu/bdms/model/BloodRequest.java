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
public class BloodRequest {
    @Id
    private String id;
    private String requesterName;
    private String bloodGroup;
    private int units;
    private boolean emergency;
    private String status;
    private String hospitalLocation;
    private LocalDate requiredDate;
}
