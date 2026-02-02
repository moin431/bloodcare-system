package bd.edu.seu.bdms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatInboxDto {

    private String roomId;
    private String otherName;
    private long unread;

    public ChatInboxDto(String roomId, String otherName, long unread) {
        this.roomId = roomId;
        this.otherName = otherName;
        this.unread = unread;
    }
}
