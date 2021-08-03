/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations.User;

import java.util.List;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.help.HelpData;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author Dunja
 */
public class LoginUser extends AbstractGenericOperation {
    
    private User user;
    
    @Override
    protected void preconditions(Object param) throws Exception {

        List<User> users;
        user = new User();

        users = repository.getAll(new User());
        String username = ((User) param).getUsername();
        String password = ((User) param).getPassword();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("1:" + user.getUsername());
                System.out.println("2:" + username);
                if (user.getPassword().equals(password)) {
                    this.user = user;
                    HelpData.loggedUser = user;
                    return;
                }
                throw new Exception("Incorrect username/password");
            }
        }

        throw new Exception("Incorrect username/password");

    }

    @Override
    public void executeOperation(Object param) throws Exception {
    }

    public User getUser() {
        return user;
    }
    
}
