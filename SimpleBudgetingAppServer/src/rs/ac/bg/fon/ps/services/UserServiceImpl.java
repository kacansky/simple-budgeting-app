package rs.ac.bg.fon.ps.services;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.User.GetAllUsers;
import rs.ac.bg.fon.ps.operations.User.InsertUser;
import rs.ac.bg.fon.ps.operations.User.LoginUser;
import rs.ac.bg.fon.ps.operations.User.UpdateUser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dunja
 */
public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;

    public UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if(instance==null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }
    
    @Override
    public List<User> getAllUsers() throws Exception {
        GetAllUsers getAllUsers = new GetAllUsers();
        getAllUsers.execute(new User());
        
        return getAllUsers.getAllUsers();
    }

    @Override
    public void updateUser(User user) throws Exception {
        UpdateUser updateUser = new UpdateUser();
        updateUser.execute(user);
    }

    @Override
    public void createUser(User user) throws Exception {
        ArrayList<User> existingUsers = (ArrayList<User>) getAllUsers();
        for (User existingUser : existingUsers) {
            if(existingUser.getUsername().equals(user.getUsername())) {
                throw new Exception("This username is already taken. Try another one.");
            }
        }
        InsertUser insertUser = new InsertUser();
        insertUser.execute(user);
    }

    @Override
    public User loginUser(User user) throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.execute(user);
        return loginUser.getUser();
    }
    
}
