/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations.Wallet;

import rs.ac.bg.fon.ps.domain.Wallet;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author Dunja
 */
public class InsertWallet extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object object) throws Exception {
        if (!(object instanceof Wallet)) {
            throw new Exception("ERROR: Invalid wallet data");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        this.repository.insert((Wallet) object);
    }
    
}
