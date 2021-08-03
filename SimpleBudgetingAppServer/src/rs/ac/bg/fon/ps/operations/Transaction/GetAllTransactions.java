/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations.Transaction;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Transaction;
import rs.ac.bg.fon.ps.domain.Wallet;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author Dunja
 */
public class GetAllTransactions extends AbstractGenericOperation {
    
    List<Transaction> allTransactions;
    
    @Override
    protected void preconditions(Object object) throws Exception {
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        Long wallet = (Long) object;
        this.allTransactions = this.repository.getAllFiltered(new Transaction(), wallet);

    }

    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }
}
