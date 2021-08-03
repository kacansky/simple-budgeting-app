/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.domain.Currency;
import rs.ac.bg.fon.ps.domain.Transaction;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.domain.Wallet;
import rs.ac.bg.fon.ps.forms.FrmStartServer;
import rs.ac.bg.fon.ps.server.Server;
import rs.ac.bg.fon.ps.services.ServiceRegistry;

/**
 *
 * @author Dunja
 */
public class Controller {

    private static Controller instance;
    private Server server;
    private final ControllerFrmStartServer controllerStartServerForm;
    private List<User> activeUsers;

    private Controller() {
        this.controllerStartServerForm = new ControllerFrmStartServer(new FrmStartServer());
        this.activeUsers = new ArrayList<>();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public User login(User user) throws Exception {
        User userToReturn = ServiceRegistry.getUserService().loginUser(user);
        if (userToReturn != null) {
            if (!this.activeUsers.contains(userToReturn)) {
                this.addLoggedInUser(userToReturn);
                return userToReturn;
            } else {
                throw new Exception("User already logged in!");
            }
        }
        throw new Exception("This user doesn't exist.");
    }

    public void createUser(User userToAdd) throws Exception {
        ServiceRegistry.getUserService().createUser(userToAdd);
    }

    public void createWallet(Wallet walletToAdd) throws Exception {
        ServiceRegistry.getWalletService().createWallet(walletToAdd);
    }

    public void createTransaction(Transaction transaction) throws Exception {
        ServiceRegistry.getTransactionService().createTransaction(transaction);
    }
    
    public void createCategory(Category category) throws Exception {
        ServiceRegistry.getCategoryService().createCategory(category);
    }

    public void stopServer() throws IOException {
        this.activeUsers = new ArrayList<>();
        this.refreshUserTable();
        this.server.stopServer();

    }

    private void addLoggedInUser(User user) {
        this.activeUsers.add(user);
        this.refreshUserTable();
    }

    private synchronized void refreshUserTable() {
        this.controllerStartServerForm.setUsers(activeUsers);
        this.controllerStartServerForm.refreshTable();
    }

    public void startServer() {
        this.server = new Server();
        
        this.server.start();
    }

    public void openForm() {
        this.controllerStartServerForm.openForm();
    }

    public void logoutUser(User user) {
        this.activeUsers.remove(user);
        this.refreshUserTable();
    }

    public List<Category> getCategoriesForUser(Long currentUserID) throws Exception {
        List<Category> categories = ServiceRegistry.getCategoryService().getAllCategories(currentUserID);
        if (categories != null) {
            return categories;
        }
        throw new Exception("Categories cannot be loaded");
    }

    public List<Currency> getAllCurrencies() throws Exception {
        List<Currency> currencies = ServiceRegistry.getCurrencyService().getAllCurrencies();
        if (currencies != null) {
            return currencies;
        }
        throw new Exception("Currencies cannot be loaded");
    }

    public void saveNewUser(User user) throws Exception {
        ServiceRegistry.getUserService().createUser(user);
    }

    public void updateUser(User userToUpdate) throws Exception {
        ServiceRegistry.getUserService().updateUser(userToUpdate);
    }

    public void saveNewWallet(Wallet walletToSave) throws Exception {
        ServiceRegistry.getWalletService().createWallet(walletToSave);
    }

    public List<Transaction> getAllTransactions(Long wallet) throws Exception {
        List<Transaction> transactions = ServiceRegistry.getTransactionService().getAllTransactions(wallet);
        if(transactions!=null){
            return transactions;
        }
        throw new Exception("Transactions cannot be loaded");
    }

    public List<Wallet> getAllWallets(User userArg) throws Exception {
        List<Wallet> wallets = ServiceRegistry.getWalletService().getAllWallets(userArg);
        if(wallets!=null){
            return wallets;
        }
        throw new Exception("Wallets cannot be loaded");
    }

    public void deleteCategory(Category categoryToDelete) throws Exception {
        ServiceRegistry.getCategoryService().deleteCategory(categoryToDelete);
    }

    public void deleteWallet(Wallet walletToDelete) throws Exception {
        ServiceRegistry.getWalletService().deleteWallet(walletToDelete);
    }

    public void deleteTransaction(Transaction transactionToDelete) throws Exception {
        ServiceRegistry.getTransactionService().deleteTransaction(transactionToDelete);
    }

    public void updateWallet(Wallet walletToUpdate) throws Exception {
        ServiceRegistry.getWalletService().updateWallet(walletToUpdate);
    }

    public void deleteTransactions(ArrayList<Transaction> transactionsToDelete) {
        ServiceRegistry.getTransactionService().deleteTransactions(transactionsToDelete);
    }

    public void addTransactions(ArrayList<Transaction> transactionsToAdd) {
        ServiceRegistry.getTransactionService().saveTransactions(transactionsToAdd);
    }

    public void saveCategories(ArrayList<Category> categoriesToSave) {
        ServiceRegistry.getCategoryService().saveCategories(categoriesToSave);
    }

    public void deleteCategories(ArrayList<Category> categoriesToDelete) {
        ServiceRegistry.getCategoryService().deleteCategories(categoriesToDelete);
    }

}