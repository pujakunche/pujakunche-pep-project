package Controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }
 
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/messages",this::postMessageHandler);
        app.post("/register", this::createUserHandler);
       app.post("/login", this::loginHandler);
       app.patch("messages/{messageId}", this::updateMessageHandler);
       app.get("messages/{messageId}", this::getMessageHandler);
       app.get("messages", this::getAllMessageHandler);
       app.delete("messages/{messageId}", this::deleteMessageHandler);
          return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws Exception 
     */
   
     private void postMessageHandler(Context ctx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createMessage = messageService.createMessage(message);
        if(createMessage!=null){
            ctx.json(mapper.writeValueAsString(createMessage));
        }else{
            ctx.status(400);
        }
    }

    private void createUserHandler(Context ctx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registerUser = accountService.registerUser(account);
        if(registerUser!=null){
            ctx.json(mapper.writeValueAsString(registerUser));
        }else{
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginUser = accountService.loginUser(account);
        if(loginUser!=null){
            ctx.json(mapper.writeValueAsString(loginUser));
        }else{
            ctx.status(401);
        }
    }
    
    private void updateMessageHandler(Context ctx) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        String messageId = ctx.pathParam("messageId");
        int convertedMessageId = Integer.parseInt(messageId);
        Message updateMessage = messageService.updateMessage(message, convertedMessageId);
        if(updateMessage!=null){
            ctx.json(mapper.writeValueAsString(updateMessage));
        }else{
            ctx.status(400);
        }
    }

    private void getMessageHandler(Context ctx) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        // Message message = mapper.readValue(ctx.body(), Message.class);
        String messageId = ctx.pathParam("messageId");
        int convertedMessageId = Integer.parseInt(messageId);
        Message fetchMessage = messageService.fetchMessage(convertedMessageId);
        if(fetchMessage!=null){
            ctx.json(mapper.writeValueAsString(fetchMessage));
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessageHandler(Context ctx) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        List<Message> fetchAllMessage = messageService.fetchAllMessage();
        if(fetchAllMessage!=null){
            ctx.json(mapper.writeValueAsString(fetchAllMessage));
        }else{
            ctx.status(400);
        }
    }

    private void deleteMessageHandler(Context ctx) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        // Message message = mapper.readValue(ctx.body(), Message.class);
        String messageId = ctx.pathParam("messageId");
        int convertedMessageId = Integer.parseInt(messageId);
        Message deleteMessage = messageService.deleteMessage(convertedMessageId);
        if(deleteMessage!=null){
            ctx.json(mapper.writeValueAsString(deleteMessage));
        }else{
            ctx.status(400);
        }
    }


    }





