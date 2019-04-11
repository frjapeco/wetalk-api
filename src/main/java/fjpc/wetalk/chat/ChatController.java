package fjpc.wetalk.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;

    @SubscribeMapping("/chat/startup")
    public List<String> startup() {
        return userRegistry.getUsers()
                .stream()
                .map(SimpUser::getName)
                .collect(toList());
    }

    @MessageMapping("/chat/{channel}")
    public Message sendToAll(@Payload String content, @DestinationVariable String channel, Principal principal) {
        String from = principal.getName();
        String to = "all";
        return new Message(from,to,content);
    }

    @MessageMapping("/chat/{username}/private")
    @SendToUser("/queue/chat/private")
    public Message sendToUser(@Payload String content, @DestinationVariable String username, Principal principal) {
        String from = principal.getName();
        Message message = new Message(from,username,content);
        if (!message.getFrom().equals(message.getTo())) {
            messagingTemplate.convertAndSendToUser(message.getTo(), "/queue/chat/private", message);
        }
        return message;
    }

}