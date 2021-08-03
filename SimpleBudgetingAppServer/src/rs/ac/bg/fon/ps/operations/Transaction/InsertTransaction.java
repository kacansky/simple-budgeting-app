/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations.Transaction;

import rs.ac.bg.fon.ps.domain.Transaction;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author Dunja
 */
public class InsertTransaction extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object object) throws Exception {
        if (!(object instanceof Transaction)) {
            throw new Exception("ERROR: Invalid transaction data");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        this.repository.insert((Transaction) object);
    }
    
}
