package bd.edu.seu.bdms.repository;
import bd.edu.seu.bdms.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByRoomIdOrderByTimeStampAsc(String roomId);

    List<ChatMessage> findBySenderId(String senderId);

    @Query(value = "{ $or: [ { 'senderId': ?0 }, { 'receiverId': ?0 } ] }",
            fields="{ 'roomId' : 1 }")
    List<ChatMessage> findRoomsForUser(String userId);

    long countByRoomIdAndReceiverIdAndSeenFalse(String roomId, String receiverId);

    long countByReceiverIdAndSeenFalse(String receiverId);

    List<ChatMessage> findByRoomIdAndReceiverIdAndSeenFalse(String roomId, String receiverId);
}
