package bd.edu.seu.bdms.service;

import bd.edu.seu.bdms.model.ChatMessage;
import bd.edu.seu.bdms.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatRepo;

    public ChatMessageService(ChatMessageRepository chatRepo) {
        this.chatRepo = chatRepo;
    }

    public ChatMessage save(ChatMessage message){
        return chatRepo.save(message);
    }

    public List<ChatMessage> getMessages(String roomId){
        return chatRepo.findByRoomIdOrderByTimeStampAsc(roomId);
    }

    public List<String> findDistinctRooms(String userId){

        return chatRepo.findRoomsForUser(userId)
                .stream()
                .map(ChatMessage::getRoomId)
                .distinct()
                .toList();
    }

    public long countUnread(String roomId, String userId){
        return chatRepo.countByRoomIdAndReceiverIdAndSeenFalse(roomId, userId);
    }

    public long countUnreadForUser(String userId){
        return chatRepo.countByReceiverIdAndSeenFalse(userId);
    }

    public void markMessagesAsSeen(String roomId, String receiverId){

        List<ChatMessage> msgs =
                chatRepo.findByRoomIdAndReceiverIdAndSeenFalse(roomId, receiverId);

        for(ChatMessage m : msgs){
            m.setSeen(true);
        }

        chatRepo.saveAll(msgs);
    }
}
