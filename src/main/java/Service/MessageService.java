package Service;

import java.util.Optional;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
       
    MessageDAO messageDao;
    AccountDAO accountDAO;

    public MessageService(){
        this.messageDao = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }


    public Message createMessage(Message message, int id) throws Exception{
       Optional<Account> fetchUser = Optional.of(accountDAO.findUserById(id));
       if(fetchUser.isPresent()){
            Message newMessage = new Message();
            newMessage.setMessage_text(message.getMessage_text());
            newMessage.setPosted_by(id);
            newMessage.setTime_posted_epoch(message.getTime_posted_epoch());
            Message result = messageDao.insertMessage(newMessage);
            return result;
       }else{
        System.out.println("no user found");
       }




        return null;
        
     }
    // public Message createMessage(Message message) throws Exception{
    //    try {
    //         Message result = messageDao.insertMessage(message);
    //         return result;
    //    } catch(Exception e){
    //         throw new Exception(e.getMessage());
    //    }
    // }


}
