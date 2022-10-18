package it.itresources.springtut.springtutorial.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServiceSocket {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ServiceSocket(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public String exceptionHandler (Throwable exception)
    {
        this.messagingTemplate.convertAndSend("/errors", exception.getMessage());
        return exception.getMessage();
    }

    public void sendMessageToLocation (String chat, Long classroomId)
    {
        String destination="/topic/"+classroomId;
        this.messagingTemplate.convertAndSend(destination, chat);
    }
}
