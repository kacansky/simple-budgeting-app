/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.domain.Currency;
import rs.ac.bg.fon.ps.domain.Transaction;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.domain.Wallet;

/**
 *
 * @author Dunja
 */
public class CommunicationController {

    private static CommunicationController instance;

    private CommunicationController() {
    }

    public static CommunicationController getInstance() {
        if (instance == null) {
            instance = new CommunicationController();
        }
        return instance;
    }

    public User login(String username, String password) throws Exception {
        return Communication.getInstance().login(username, password);
    }
    
    public void logout(User user) throws Exception {
        Communication.getInstance().logout(user);
    }

    public List<Currency> getCurrencies() throws Exception {
        return Communication.getInstance().getCurrencies();
    }

    public void saveNewWallet(Wallet wallet) throws Exception {
        Communication.getInstance().saveNewWallet(wallet);
    }

    public List<Wallet> getWallets(User user) throws Exception {
        List<Wallet> wallets;
        wallets = Communication.getInstance().getWallets(user);
        return wallets;
    }

    public List<Category> getCategories(Long userid) throws Exception {
        return Communication.getInstance().getCategories(userid);
    }

    public void deleteTransactions(ArrayList<Transaction> deletedTransactions) throws Exception {
        Communication.getInstance().deleteTransactions(deletedTransactions);
    }

    public void addTransactions(ArrayList<Transaction> addedTransactions) throws Exception {
        Communication.getInstance().addTransactions(addedTransactions);

    }

    public void updateWallet(Wallet wallet) throws Exception {
        Communication.getInstance().updateWallet(wallet);
    }

    public void deleteCategories(ArrayList<Category> deletedCategories) throws Exception {
        Communication.getInstance().deleteCategories(deletedCategories);
    }

    public void addCategories(ArrayList<Category> addedCategories) throws Exception {
        Communication.getInstance().addCategories(addedCategories);
    }

    public void deleteWallet(Wallet walletToDelete) throws Exception {
        Communication.getInstance().deleteWallet(walletToDelete);
    }
}
