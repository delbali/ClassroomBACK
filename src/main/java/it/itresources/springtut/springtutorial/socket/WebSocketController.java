package it.itresources.springtut.springtutorial.socket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
    public void processMessageFromClient(@Payload String message) {
        System.out.println("Messaggio (controller del ws)");
        System.out.println(message);
        String chat= new Gson().fromJson(message, Map.class).get("name").toString()+": "+new Gson().fromJson(message, Map.class).get("message").toString();

        String classroom= idConverter(new Gson().fromJson(message, Map.class).get("classroomid").toString());
        //String user=idConverter(new Gson().fromJson(message, Map.class).get("userId").toString());
        System.out.println(chat);
        System.out.println("-"+classroom+"-");
        messagingTemplate.convertAndSend("/topic.reply"+classroom, chat);
    }

    @MessageExceptionHandler
    public ResponseEntity<?> handleException(Throwable exception) {
        System.out.println("Eccezione: "+exception.getMessage());
        messagingTemplate.convertAndSend("/errors", exception.getMessage());
        return ResponseEntity.badRequest().body(exception.getMessage());
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

    public static String idConverter (String id){
        Integer counter=0;
        for (int i=0; i<id.length(); i++)
        {
            if (id.charAt(i)=='.')
            {
                counter=i;
            }
        }
        return id.substring(0,counter);
    }
}
