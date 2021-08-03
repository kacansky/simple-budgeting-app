/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations.User;

import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author Dunja
 */
public class InsertUser extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object object) throws Exception {
        if (!(object instanceof User)) {
            throw new Exception("ERROR: Invalid user data");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        this.repository.insert((User) object);
    }
    
}
