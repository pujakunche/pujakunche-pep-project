package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account registerUser(Account account){
        Account registerUser = accountDAO.creatingUser(account);
        return registerUser;
    }

    public Account loginUser(String username , String password){
        Account fetchUser = accountDAO.findUserByUsernameAndPassword(username,password);
        return fetchUser;

    }

}
