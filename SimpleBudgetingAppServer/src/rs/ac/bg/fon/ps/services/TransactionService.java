/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.services;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Transaction;
import rs.ac.bg.fon.ps.domain.Wallet;

/**
 *
 * @author Dunja
 */
public interface TransactionService {
    
    List<Transaction> getAllTransactions(Long wallet) throws Exception;

    void createTransaction(Transaction transaction) throws Exception;
    
    void deleteTransaction(Transaction transaction) throws Exception;
    
    void updateTransaction(Transaction transaction) throws Exception;

    public void saveTransactions(ArrayList<Transaction> transactionsToAdd);

    public void deleteTransactions(ArrayList<Transaction> transactionsToDelete);
    
}
