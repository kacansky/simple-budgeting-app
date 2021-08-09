/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.threads;

import rs.ac.bg.fon.ps.controller.Controller;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.communication.ClientRequest;
import rs.ac.bg.fon.ps.communication.Receiver;
import rs.ac.bg.fon.ps.communication.Sender;
import rs.ac.bg.fon.ps.communication.ServerResponse;
import rs.ac.bg.fon.ps.constants.Operation;
import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.domain.Currency;
import rs.ac.bg.fon.ps.domain.Transaction;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.domain.Wallet;

/**
 *
 * @author Dunja
 */
public class HandlingClientsRequests extends Thread {

    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    private boolean end = false;
    private User currentUser = new User();

    public HandlingClientsRequests(Socket socket) {
        this.socket = socket;
        this.sender = new Sender(this.socket);
        this.receiver = new Receiver(this.socket);
    }

    @Override
    public void run() {

        while (!end) {
            try {
                ClientRequest clientRequest = (ClientRequest) receiver.receive();
                ServerResponse serverResponse = new ServerResponse();
                try {
                    switch (clientRequest.getOperation()) {
                        case Operation.LOGIN:
                            User user = (User) clientRequest.getArgument();
                            this.currentUser = Controller.getInstance().login(user);
                            serverResponse.setResult(this.currentUser);
                            break;
                        case Operation.GET_CATEGORIES_FOR_USER:
                            Long userid = (Long) clientRequest.getArgument();
                            List<Category> categories = Controller.getInstance().getCategoriesForUser(userid);
                            System.out.println("GET CATEGORIES FOR USER: ");
                            for (Category category : categories) {
                                System.out.println(category);
                            }
                            serverResponse.setResult(categories);
                            break;
                        case Operation.GET_ALL_CURRENCIES:
                            List<Currency> currencies = Controller.getInstance().getAllCurrencies();
                            System.out.println("GET ALL CURRENCIES:");
                            for (Currency currency : currencies) {
                                System.out.println(currency);
                            }
                            serverResponse.setResult(currencies);
                            break;
                        case Operation.SAVE_NEW_USER:
                            User userToSave = (User) clientRequest.getArgument();
                            Controller.getInstance().saveNewUser(userToSave);
                            break;
                        case Operation.UPDATE_PASSWORD:
                        case Operation.UPDATE_USER_SETTINGS:
                            User userToUpdate = (User) clientRequest.getArgument();
                            Controller.getInstance().updateUser(userToUpdate);
                            this.currentUser = userToUpdate;
                            break;
                        case Operation.SAVE_NEW_WALLET:
                            Wallet walletToSave = (Wallet) clientRequest.getArgument();
                            Controller.getInstance().saveNewWallet(walletToSave);
                            break;
                        case Operation.GET_ALL_TRANSACTIONS:
                            Long walletid = (Long) clientRequest.getArgument();
                            List<Transaction> transactions = Controller.getInstance().getAllTransactions(walletid);
                            System.out.println("GET ALL TRANSACTIONS:");
                            for (Transaction transaction : transactions) {
                                System.out.println(transaction);
                            }
                            serverResponse.setResult(transactions);
                            break;
                        case Operation.GET_ALL_WALLETS:
                            User userArg = (User) clientRequest.getArgument();
                            List<Wallet> wallets = Controller.getInstance().getAllWallets(userArg);
                            System.out.println("GET ALL WALLETS:");
                            for (Wallet wallet : wallets) {
                                System.out.println(wallet);
                            }
                            serverResponse.setResult(wallets);
                            break;
                        case Operation.DELETE_WALLET:
                            Wallet walletToDelete = (Wallet) clientRequest.getArgument();
                            Controller.getInstance().deleteWallet(walletToDelete);
                            break;
                        case Operation.DELETE_TRANSACTIONS:
                            ArrayList<Transaction> transactionsToDelete = (ArrayList<Transaction>) clientRequest.getArgument();
                            Controller.getInstance().deleteTransactions(transactionsToDelete);
                            break;
                        case Operation.ADD_TRANSACTIONS:
                            ArrayList<Transaction> transactionsToAdd = (ArrayList<Transaction>) clientRequest.getArgument();
                            Controller.getInstance().addTransactions(transactionsToAdd);
                            break;
                        case Operation.UPDATE_WALLET:
                            Wallet walletToUpdate = (Wallet) clientRequest.getArgument();
                            Controller.getInstance().updateWallet(walletToUpdate);
                            break;
                        case Operation.LOGOUT:
                            User userToLogOut = (User) clientRequest.getArgument();
                            Controller.getInstance().logoutUser(userToLogOut);
                            this.end = true;
                            if (this.socket.isConnected()) {
                                this.socket.close();
                            }
                            return;
                        case Operation.SAVE_CATEGORIES:
                            ArrayList<Category> categoriesToSave = (ArrayList<Category>) clientRequest.getArgument();
                            Controller.getInstance().saveCategories(categoriesToSave);
                            break;
                        case Operation.DELETE_CATEGORIES:
                            ArrayList<Category> categoriesToDelete = (ArrayList<Category>) clientRequest.getArgument();
                            Controller.getInstance().deleteCategories(categoriesToDelete);
                            break;

                    }
                } catch (Exception e) {
                    serverResponse.setException(e);
                    System.out.println("CAUGHT EXCEPTION");
                }
                this.sender.send(serverResponse);
            } catch (Exception ex) {
                this.end = true;
                Logger.getLogger(HandlingClientsRequests.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        try {
            if (this.socket != null && !this.socket.isClosed()) {
                Controller.getInstance().logoutUser(currentUser);
                this.socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(HandlingClientsRequests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void serverStopped() {
        this.end = true;
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(HandlingClientsRequests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
