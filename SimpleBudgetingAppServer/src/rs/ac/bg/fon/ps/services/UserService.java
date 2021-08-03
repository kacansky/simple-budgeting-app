/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.services;

import java.util.List;
import rs.ac.bg.fon.ps.domain.User;

/**
 *
 * @author Dunja
 */
public interface UserService {
    
    User loginUser(User user) throws Exception;
    
    List<User> getAllUsers() throws Exception;

    void updateUser(User user) throws Exception;

    void createUser(User user) throws Exception;
}
