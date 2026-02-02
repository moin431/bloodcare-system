package bd.edu.seu.bdms.controller;

import bd.edu.seu.bdms.dto.ChatInboxDto;
import bd.edu.seu.bdms.model.ChatMessage;
import bd.edu.seu.bdms.model.Donor;
import bd.edu.seu.bdms.model.User;
import bd.edu.seu.bdms.service.ChatMessageService;
import bd.edu.seu.bdms.service.DonorService;
import bd.edu.seu.bdms.service.UserService;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    private final ChatMessageService chatService;
    private final UserService userService;
    private final DonorService donorService;

    public ChatController(ChatMessageService chatService, UserService userService, DonorService donorService) {
        this.chatService = chatService;
        this.userService = userService;
        this.donorService = donorService;
    }
    @ModelAttribute("unreadCount")
    public long unread(Authentication auth){
        User u = userService.findUserByEmail(auth.getName());
        return chatService.countUnreadForUser(u.getId());
    }


    @GetMapping("/chat/start/{donorId}")
    public String startChat(@PathVariable String donorId, Authentication auth){

        User current = userService.findUserByEmail(auth.getName());

        Donor donor = donorService.findById(donorId);

        String id1 = current.getId();
        String id2 = donor.getUserId();   // ✅ IMPORTANT

        String roomId = id1.compareTo(id2) < 0
                ? id1 + "_" + id2
                : id2 + "_" + id1;

        return "redirect:/chat/" + roomId;
    }

    @GetMapping("/my-chats")
    public String myChats(Authentication auth, Model model){

        User user = userService.findUserByEmail(auth.getName());

        List<String> rooms = chatService.findDistinctRooms(user.getId());

        List<ChatInboxDto> inbox = new ArrayList<>();

        for(String room : rooms){

            String[] ids = room.split("_");

            String otherId = ids[0].equals(user.getId()) ? ids[1] : ids[0];

            User other = userService.findOptionalById(otherId);

            if(other == null) continue;

            long unread = chatService.countUnread(room, user.getId());

            inbox.add(new ChatInboxDto(
                    room,
                    other.getName(),   // ✅ MUST BE NAME
                    unread
            ));
        }

        model.addAttribute("inbox", inbox);

        return "my-chats";
    }

    @GetMapping("/chat/{roomId}")
    public String chatRoom(@PathVariable String roomId, Model model, Authentication auth){

        List<ChatMessage> messages = chatService.getMessages(roomId);

        String[] ids = roomId.split("_");

        User current = userService.findUserByEmail(auth.getName());

        String otherUserId = ids[0].equals(current.getId()) ? ids[1] : ids[0];

        User otherUser = userService.findOptionalById(otherUserId);

        chatService.markMessagesAsSeen(roomId, current.getId());

        model.addAttribute("roomId", roomId);
        model.addAttribute("messages", messages);
        model.addAttribute("otherUser", otherUser);
        model.addAttribute("currentUserId", current.getId());

        return "chat";
    }




    @GetMapping("/chat/list")
    public String chatList(Authentication auth, Model model){

        User user = userService.findUserByEmail(auth.getName());

        List<String> rooms = chatService.findDistinctRooms(user.getId());

        model.addAttribute("rooms", rooms);

        return "chat-list";
    }

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage sendMessage(
            @DestinationVariable String roomId,
            ChatMessage message){

        String[] ids = roomId.split("_");

        String receiverId = ids[0].equals(message.getSenderId())
                ? ids[1]
                : ids[0];

        message.setRoomId(roomId);
        message.setReceiverId(receiverId);
        message.setTimeStamp(LocalDateTime.now());
        message.setSeen(false);
        message.setEmergency(false);

        chatService.save(message);

        return message;
    }









}
