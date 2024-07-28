package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    //message_id int primary key auto_increment,
    // posted_by int,
    // message_text varchar(255),
    // time_posted_epoch bigint,
    // foreign key (posted_by) references  account(account_id)


    public Message insertMessage(Message message){

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) values (?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public Message findMessageById(int messageId){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
    
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            preparedStatement.setInt(1, messageId);
    
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message();
                    message.setMessage_id(rs.getInt("message_id"));
                    message.setMessage_text(rs.getString("message_text"));
                    message.setPosted_by(rs.getInt("posted_by"));
                    message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                return message;
            } 
        }catch(SQLException e){
                System.out.println(e.getMessage());
        }
        return null;
    }

    public Message findMessageByText(String text){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_text = ?";
    
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            preparedStatement.setString(1, text);
    
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message();
                    message.setMessage_id(rs.getInt("message_id"));
                    message.setMessage_text(rs.getString("message_text"));
                    message.setPosted_by(rs.getInt("posted_by"));
                    message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                return message;
            } 
        }catch(SQLException e){
                System.out.println(e.getMessage());
        }
        return null;
    }


    public Message updateMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            int tempMessageId = message.getMessage_id();
            String sql = "UPDATE message SET message_text = ? WHERE  message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message.getMessage_id());
            //If void is expected
            preparedStatement.executeUpdate();

           Message returnMessage = this.findMessageById(tempMessageId);
            
            // ResultSet rs = preparedStatement.executeUpdate();
                // Message returnMessage = new Message(rs.getInt("flight_id"), rs.getString("departure_city"),
                //         rs.getString("arrival_city"));
                // Message returnMessage = new Message();
                // message.setMessage_id(rs.getInt("message_id"));
                // message.setMessage_text(rs.getString("message_text"));
                // message.setPosted_by(rs.getInt("posted_by"));
                // message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
            
            return returnMessage;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> listOfAllMessages = new ArrayList<>();
        try {
            String sql = "select * from message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message();
                    message.setMessage_id(rs.getInt("message_id"));
                    message.setMessage_text(rs.getString("message_text"));
                    message.setPosted_by(rs.getInt("posted_by"));
                    message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));       
                listOfAllMessages.add(message);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listOfAllMessages;
    }

    public void removeMessage(int messageId){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, messageId);
            //If void is expected
            preparedStatement.executeUpdate();

        //    Message returnMessage = this.findMessageById(tempMessageId);
            
            // ResultSet rs = preparedStatement.executeUpdate();
                // Message returnMessage = new Message(rs.getInt("flight_id"), rs.getString("departure_city"),
                //         rs.getString("arrival_city"));
                // Message returnMessage = new Message();
                // message.setMessage_id(rs.getInt("message_id"));
                // message.setMessage_text(rs.getString("message_text"));
                // message.setPosted_by(rs.getInt("posted_by"));
                // message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
            
            // return returnMessage;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        // return null;
    }


}
