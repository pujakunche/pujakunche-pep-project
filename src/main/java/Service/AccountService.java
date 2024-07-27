package Service;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;
import io.javalin.http.UnauthorizedResponse;

public class AccountService {
    
    AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account registerUser(Account account){
        if((!account.getUsername().isBlank()) && (account.getPassword().length() > 4)){

        Boolean checkResult = this.duplicateUsername(account.getUsername()) ;
           if(checkResult){
            System.out.println("duplicate");
            return null;
           } else{
            Account registerUser = accountDAO.creatingUser(account);

            return registerUser; 
           }
        }else{
            System.out.println("password & username cant be null");
            return null;
        }
        
    }
    
    public Boolean duplicateUsername(String username){
        Account usernamList = accountDAO.findDuplicateUsernames(username);
        Boolean isDuplicated = false;
        if(usernamList == null){
            return isDuplicated;
        }else{
            isDuplicated = true;
            return isDuplicated;
        }
    }

    public Account loginUser(Account account) throws UnauthorizedResponse{
        try {
            Account fetchUser = accountDAO.findUserByUsernameAndPassword(account);
            return fetchUser;

        } catch (UnauthorizedResponse e){
            // throw new UnauthorizedResponse(e.getMessage());
            return null;
        }

        //return it to be null


    }

}
