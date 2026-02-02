package bd.edu.seu.bdms.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ChatMessage {
    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private String roomId;
    private String content;
    private LocalDateTime timeStamp;
    private Boolean emergency;
    private Boolean seen;

}
