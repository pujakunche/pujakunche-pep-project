package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
        // app.get("example-endpoint", this::exampleHandler);
        app.post("/message",this::postMessageHandler);
        app.post("/register", this::createUserHandler);
       app.get("/login", this::loginHandler);
          return app;
    }

    // public void startAPI(){
    //     Javalin app = Javalin.create();
    //     app.get("/books", this::getAllBooksHandler);
    //     app.post("/books", this::postBookHandler);
    //     app.get("/authors", this::getAllAuthorsHandler);
    //     app.post("/authors", this::postAuthorHandler);
    //     app.get("/books/available", this::getAvailableBooksHandler);
    //     app.start(8080);
    // }

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

    private void loginHandler(Context ctx) {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        // String username password
        Account loginAccount = accountService.loginUser();
       
        if(loginAccount!=null){
            ctx.json(mapper.writeValueAsString(loginAccount));
        }else{
            ctx.status(400);
        }

    }

    // private void postAuthorHandler(Context ctx) throws JsonProcessingException {
    //     ObjectMapper mapper = new ObjectMapper();
    //     Author author = mapper.readValue(ctx.body(), Author.class);
    //     Author addedAuthor = authorService.addAuthor(author);
    //     if(addedAuthor!=null){
    //         ctx.json(mapper.writeValueAsString(addedAuthor));
    //     }else{
    //         ctx.status(400);
    //     }
    // }


}