package Service;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
       
    MessageDAO messageDao;

    public MessageService(){
        this.messageDao = new MessageDAO();
    }

    public Message createMessage(Message message) throws Exception{
       try {
            Message result = messageDao.insertMessage(message);
            return result;
       } catch(Exception e){
            throw new Exception(e.getMessage());
       }
    }


}
