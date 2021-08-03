/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.domain.Transaction;
import rs.ac.bg.fon.ps.domain.Wallet;
import rs.ac.bg.fon.ps.operations.Transaction.DeleteTransaction;
import rs.ac.bg.fon.ps.operations.Transaction.GetAllTransactions;
import rs.ac.bg.fon.ps.operations.Transaction.InsertTransaction;
import rs.ac.bg.fon.ps.operations.Transaction.UpdateTransaction;

/**
 *
 * @author Dunja
 */
public class TransactionServiceImpl implements TransactionService {

    private static TransactionServiceImpl instance;

    private TransactionServiceImpl() {
    }

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionServiceImpl();
        }
        return instance;
    }
    
    @Override
    public List<Transaction> getAllTransactions(Long wallet) throws Exception {
        GetAllTransactions getAllTransactions = new GetAllTransactions();
        getAllTransactions.execute(wallet);
        return getAllTransactions.getAllTransactions();
    }

    @Override
    public void createTransaction(Transaction transaction) throws Exception {
        InsertTransaction insertTransaction = new InsertTransaction();
        insertTransaction.execute(transaction);
    }

    @Override
    public void deleteTransaction(Transaction transaction) throws Exception {
        DeleteTransaction deleteTransaction = new DeleteTransaction();
        deleteTransaction.execute(transaction);
    }

    @Override
    public void updateTransaction(Transaction transaction) throws Exception {
        UpdateTransaction updateTransaction = new UpdateTransaction();
        updateTransaction.execute(transaction);
    }

    @Override
    public void saveTransactions(ArrayList<Transaction> transactionsToAdd) {
        for (Transaction transaction : transactionsToAdd) {
            try {
                createTransaction(transaction);
            } catch (Exception ex) {
                Logger.getLogger(TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void deleteTransactions(ArrayList<Transaction> transactionsToDelete) {
        for (Transaction transaction : transactionsToDelete) {
            try {
                deleteTransaction(transaction);
            } catch (Exception ex) {
                Logger.getLogger(TransactionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
