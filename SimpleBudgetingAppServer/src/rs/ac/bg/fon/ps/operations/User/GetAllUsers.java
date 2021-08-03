/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations.User;

import java.util.List;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author Dunja
 */
public class GetAllUsers extends AbstractGenericOperation {
    
    private List<User> allUsers;
    
    @Override
    protected void preconditions(Object object) throws Exception {
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        this.allUsers = this.repository.getAll(new User());
    }

    public List<User> getAllUsers() {
        return allUsers;
    }
    
}
