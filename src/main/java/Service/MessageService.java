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


    public Message createMessage(Message message) throws Exception{
       Optional<Account> fetchUser = Optional.of(accountDAO.findUserById(message.getPosted_by()));
       if(fetchUser.isPresent()){
            Message newMessage = new Message();
            newMessage.setMessage_text(message.getMessage_text());
            newMessage.setPosted_by(message.getPosted_by());
            newMessage.setTime_posted_epoch(message.getTime_posted_epoch());

            if((!newMessage.getMessage_text().isBlank()) && (message.getMessage_text().length() < 225)){
                Message result = messageDao.insertMessage(newMessage);
                return result;
            }else {
                System.out.println("Message text field blank");
                return null;
            }

       }else{
        System.out.println("no user found");
        return null;
       }
     }

     public Message updateMessage(Message message, int accountId, int messageId){
        //Looking for user if account exists
        Optional <Account> fetchUser = Optional.of(accountDAO.findUserById(accountId));
        if(fetchUser.isPresent()){
            Optional<Message> findMessage = Optional.of(messageDao.findMessageById(messageId));
            if(findMessage.isPresent()){
                Message updatedMessage = new Message();
                updatedMessage.setMessage_text(message.getMessage_text());
                updatedMessage.setPosted_by(accountId);
                updatedMessage.setPosted_by(messageId);
                updatedMessage.setTime_posted_epoch(message.getTime_posted_epoch());
                Message result = messageDao.updateMessage(updatedMessage);

                // Optional<Message> verifyMessage = Optional.of(messageDao.findMessageById(messageId));
                return result;
            } else {
                System.out.println("No message found");
                return null;
            }
       }else{
        System.out.println("no user found");
        return null;
       }
     }

     }



