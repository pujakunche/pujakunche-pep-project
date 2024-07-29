package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import Model.Account;
import Util.ConnectionUtil;
import io.javalin.http.UnauthorizedResponse;

public class AccountDAO {


    public Account creatingUser(Account account){

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(username, password) values (?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account findUserByUsernameAndPassword(Account account) throws UnauthorizedResponse{
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());


            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account result  = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                return result;
            } 
        }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            throw new UnauthorizedResponse();

    }
    

public Account findUserById(int id){
    try {
        String sql = "SELECT * FROM account WHERE account_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        Account account = new Account();
        while(rs.next()){
            account = new Account(rs.getInt("account_id"), rs.getString("username"),
                    rs.getString("password"));
        }
        return account;
 
    }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

}



// public List<String> getAllUsernames(){
//     Connection connection = ConnectionUtil.getConnection();
//     List<String> listOfAllMessages = new ArrayList<>();
//     try {
//         String sql = "select And from account";
//         PreparedStatement ps = connection.prepareStatement(sql);
//         ResultSet rs = ps.executeQuery();
//         while(rs.next()){
//             Message message = new Message();
//                 message.setMessage_id(rs.getInt("message_id"));
//                 message.setMessage_text(rs.getString("message_text"));
//                 message.setPosted_by(rs.getInt("posted_by"));
//                 message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));       
//             listOfAllMessages.add(message);
//         }
//     }catch(SQLException e){
//         e.printStackTrace();
//     }
//     return listOfAllMessages;
// }

public Account findDuplicateUsernames(String username){
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "SELECT * FROM account WHERE username = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, username);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                    rs.getString("password"));
            return account;
        } 
    }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

}
}
// public Account findMessageById(int accountId){
//     Connection connection = ConnectionUtil.getConnection();
//     try {
//         String sql = "SELECT * FROM account WHERE account_id = ?";

//         PreparedStatement preparedStatement = connection.prepareStatement(sql);

//         preparedStatement.setInt(1, accountId);

//         ResultSet rs = preparedStatement.executeQuery();
//         while(rs.next()){
//             Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
//                     rs.getString("password"));
//             return account;
//         } 
//     }catch(SQLException e){
//             System.out.println(e.getMessage());
//         }
//         return null;

// }

// }