package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    //message_id int primary key auto_increment,
    // posted_by int,
    // message_text varchar(255),
    // time_posted_epoch bigint,
    // foreign key (posted_by) references  account(account_id)

    
    // String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) values (?, ?, ?)" ;

    public Message insertMessage(Message message){

        Connection connection = ConnectionUtil.getConnection();
        try {
    String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) values (?, ?, ?)" ;
    PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
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
        }
        return null;
    }
}
