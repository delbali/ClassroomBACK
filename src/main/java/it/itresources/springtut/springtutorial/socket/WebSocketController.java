package it.itresources.springtut.springtutorial.socket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketController {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessageSendingOperations messagingTemplate)
    {
        this.messagingTemplate=messagingTemplate;
    }

    @MessageMapping("/message")
    @SendTo("/topic/reply")
    public String processMessageFromClient(@Payload String message) throws Exception {
        System.out.println("Messaggio (controller del ws");
        String name = new Gson().fromJson(message, Map.class).get("name").toString();
        String chat= new Gson().fromJson(message, Map.class).get("message").toString();
        System.out.println(name+": "+chat);

        return name+": "+chat;
    }

    @MessageExceptionHandler
    public String handleException(Throwable exception) {
        System.out.println("Eccezione");
        messagingTemplate.convertAndSend("/errors", exception.getMessage());
        return exception.getMessage();
    }


    /*  @PostMapping ("/message/{id}")
    public ResponseEntity<?> processMessageFromClient(@Payload String message, @PathVariable(value = "id") Long classroomId) throws Exception {
        System.out.println("Messaggio (controller del ws");
        String chat= new Gson().fromJson(message, Map.class).get("name").toString()+": "+new Gson().fromJson(message, Map.class).get("message").toString();
        System.out.println(chat);
        this.serviceSocket.sendMessageToLocation(chat, classroomId);
        return ResponseEntity.ok().build();
    }

    @MessageExceptionHandler
    public String handleException(Throwable exception) {
        System.out.println("Eccezione");
        return serviceSocket.exceptionHandler(exception);
    }*/
}
