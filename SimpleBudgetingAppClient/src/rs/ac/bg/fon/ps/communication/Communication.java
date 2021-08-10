/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;



import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import rs.ac.bg.fon.ps.constants.Operation;
import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.domain.Currency;
import rs.ac.bg.fon.ps.domain.Transaction;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.domain.Wallet;
import rs.ac.bg.fon.ps.resources.ServerConfig;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dunja
 */
public class Communication {

    private Socket socket;
    private Sender sender;
    private Receiver receiver;
    private static Communication instance;

    private Communication() throws IOException {
        this.socket = new Socket(ServerConfig.getInstance().getHost(), ServerConfig.getInstance().getServerPort());
        this.sender = new Sender(this.socket);
        this.receiver = new Receiver(this.socket);
    }

    public static Communication getInstance() throws Exception {
        if (instance == null) {
            instance = new Communication();
        }

        return instance;
    }

    private void resetConnection() throws IOException {
        this.socket = new Socket(ServerConfig.getInstance().getHost(), ServerConfig.getInstance().getServerPort());
        this.sender = new Sender(this.socket);
        this.receiver = new Receiver(this.socket);
    }

    public User login(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setOperation(Operation.LOGIN);
            clientRequest.setArgument(user);
            this.sender.send(clientRequest);

            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            if (serverResponse.getException() == null) {
                return (User) serverResponse.getResult();
            } else {
                throw serverResponse.getException();
            }
        } catch (SocketException se) {
            try {
                this.resetConnection();
                return this.login(username, password);
            } catch (Exception e) {
                throw new Exception("Cannot reach server");
            }
        }
    }

    public void logout(User user) throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setArgument(user);
        clientRequest.setOperation(Operation.LOGOUT);
        this.sender.send(clientRequest);

        this.socket.close();
        System.exit(0);
    }

    public ArrayList<Transaction> getTransactions(Long walletID) throws Exception {
        ArrayList<Transaction> transactions = new ArrayList<>();
         try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setOperation(Operation.GET_ALL_TRANSACTIONS);
            clientRequest.setArgument(walletID);
            this.sender.send(clientRequest);

            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            transactions = (ArrayList<Transaction>) serverResponse.getResult();
             for (Transaction transaction : transactions) {
                 System.out.println(transaction);
             }
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
            }
        } catch (SocketException se) {
            throw new SocketException("Server disconnected");
        }
         
        return transactions;
    }

    public ArrayList<Category> getCategories(Long userid) throws Exception {
        ArrayList<Category> categories = new ArrayList<>();
         try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setOperation(Operation.GET_CATEGORIES_FOR_USER);
            clientRequest.setArgument(userid);
            this.sender.send(clientRequest);
            
            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            categories = (ArrayList<Category>) serverResponse.getResult();
            if(categories==null) {
                System.out.println("Received NULL for some reason???");
            }
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
            }
        } catch (SocketException se) {
            throw new SocketException("Server disconnected");
        }
         
        return categories;
    }

    public void updateUser(User user) throws Exception {
         try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setArgument(user);
            clientRequest.setOperation(Operation.UPDATE_USER_SETTINGS);
            this.sender.send(clientRequest);

            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
            }
        } catch (SocketException se) {
            throw new SocketException("Server disconnected");
        }
    }

    public List<Currency> getCurrencies() throws Exception {
         try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setOperation(Operation.GET_ALL_CURRENCIES);
            this.sender.send(clientRequest);

            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
        
            if (serverResponse.getException() == null) {
                return (List<Currency>) serverResponse.getResult();
            } else {
                throw serverResponse.getException();
            }
        } catch (SocketException se) {
            throw new SocketException("Server disconnected");
        }
         
    }

    public void saveNewWallet(Wallet wallet) throws Exception {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setOperation(Operation.SAVE_NEW_WALLET);
            clientRequest.setArgument(wallet);
            this.sender.send(clientRequest);

            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
    
            }
        } catch (SocketException se) {
            throw new SocketException("Server disconnected");
        }
    }

    public void saveNewUser(User user) throws Exception {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setOperation(Operation.SAVE_NEW_USER);
            clientRequest.setArgument(user);
            this.sender.send(clientRequest);

            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
            }
        } catch (SocketException se) {
            throw new SocketException("Server disconnected");
        }
    }

    public List<Wallet> getWallets(User user) throws Exception {
         try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setArgument(user);
            clientRequest.setOperation(Operation.GET_ALL_WALLETS);
            this.sender.send(clientRequest);

            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            if (serverResponse.getException() == null) {
                return (List<Wallet>) serverResponse.getResult();
            } else {
                throw serverResponse.getException();
            }
        } catch (SocketException se) {
            throw new SocketException("Server disconnected");
        }
    }

    void deleteTransactions(ArrayList<Transaction> deletedTransactions) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setArgument(deletedTransactions);
            clientRequest.setOperation(Operation.DELETE_TRANSACTIONS); 
            this.sender.send(clientRequest);
            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
    
            }
        } catch (Exception ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addTransactions(ArrayList<Transaction> addedTransactions) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setArgument(addedTransactions);
            clientRequest.setOperation(Operation.ADD_TRANSACTIONS); 
            this.sender.send(clientRequest);
            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
    
            }
        } catch (Exception ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void updateWallet(Wallet wallet) throws SocketException {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setArgument(wallet);
            clientRequest.setOperation(Operation.UPDATE_WALLET);
            this.sender.send(clientRequest);
            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
    
            }

        } catch (SocketException se) {
            throw new SocketException("Server disconnected");
            
        } catch (Exception ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void deleteCategories(ArrayList<Category> deletedCategories) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setArgument(deletedCategories);
            clientRequest.setOperation(Operation.DELETE_CATEGORIES); 
            this.sender.send(clientRequest);
            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
    
            }
        } catch (Exception ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addCategories(ArrayList<Category> addedCategories) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setArgument(addedCategories);
            clientRequest.setOperation(Operation.SAVE_CATEGORIES); 
            this.sender.send(clientRequest);
            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
    
            }
        } catch (Exception ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void deleteWallet(Wallet walletToDelete) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setArgument(walletToDelete);
            clientRequest.setOperation(Operation.DELETE_WALLET);
            this.sender.send(clientRequest);
            ServerResponse serverResponse = (ServerResponse) this.receiver.receive();
            if (serverResponse.getException() != null) {
                throw serverResponse.getException();
    
            }
        } catch (Exception ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
