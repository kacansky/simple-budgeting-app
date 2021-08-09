/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.communication.CommunicationController;
import rs.ac.bg.fon.ps.components.table.TransactionsTableModel;
import rs.ac.bg.fon.ps.constants.Constants;
import rs.ac.bg.fon.ps.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.domain.Transaction;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.domain.Wallet;
import rs.ac.bg.fon.ps.forms.FrmCreateCategory;
import rs.ac.bg.fon.ps.forms.FrmWallet;
import rs.ac.bg.fon.ps.threads.ClockThread;

/**
 *
 * @author Dunja
 */
public class WalletController {
    private final FrmWallet frmWallet;
    Wallet wallet;
    ArrayList<Transaction> transactionsForView;
    ArrayList<Category> categories;
    double tempBalance;
    User user;
    ArrayList<Transaction> deletedTransactions;
    ArrayList<Transaction> addedTransactions;
    CreateCategoryController ccc;
    ArrayList<Transaction> filteredTransactionsForView; // Filtering on client side since they already have all the data.

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public WalletController(FrmWallet frmWallet, Wallet wallet) {
        this.frmWallet = frmWallet;
        this.wallet = wallet;
        this.addedTransactions = new ArrayList<>();
        this.deletedTransactions = new ArrayList<>();
        this.tempBalance = wallet.getBalance();
        this.filteredTransactionsForView = new ArrayList<>();
        addActionListener();
        this.user = (User) MainCoordinator.getInstance().getParam(Constants.LOGGED_USER);
        ccc = new CreateCategoryController(new FrmCreateCategory(),this);
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
        fillCategory();
    }
    
    public void openForm() {
        frmWallet.setLocationRelativeTo(null);
        frmWallet.setTitle("Wallet view");
        frmWallet.setResizable(false);
        frmWallet.setVisible(true);        
        prepareForm();

    }
    private void prepareForm() {
        frmWallet.getTxtAmount().setText("");
        frmWallet.getCbFilter().setSelected(false);
        frmWallet.getRbDateSort().setSelected(true);
        frmWallet.getRbAmountSort().setSelected(false);
        frmWallet.getRbIncome().setSelected(true);
        try {
            this.transactionsForView = Communication.getInstance().getTransactions(wallet.getWalletID());
            this.frmWallet.getTblTranscactions().setModel(new TransactionsTableModel(wallet.getCurrency(),transactionsForView));
        } catch (Exception ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
        fillCategory();
        setUpClock();
        setBalanceLabel();
        }
    

    private void fillCategory() {
        try {
            this.categories = (ArrayList<Category>) CommunicationController.getInstance().getCategories(user.getUserID());
            this.frmWallet.getCmbCategory().setModel(new DefaultComboBoxModel<>(this.categories.toArray()));
            this.frmWallet.getCmbCategoryFilter().setModel(new DefaultComboBoxModel<>(this.categories.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void addActionListener() {
        frmWallet.rbSortDateDescendingAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(frmWallet.getRbDateSort().isSelected()){
                    Collections.sort(transactionsForView,Comparator.comparing(Transaction::getDate).reversed());
                    TransactionsTableModel ttm = new TransactionsTableModel(wallet.getCurrency(), transactionsForView);
                    frmWallet.getTblTranscactions().setModel(ttm);
                }
            }
        });
        frmWallet.rbSortAmountAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(frmWallet.getRbAmountSort().isSelected()){
                    Collections.sort(transactionsForView,Comparator.comparing(Transaction::getAmount).reversed());
                    TransactionsTableModel ttm = new TransactionsTableModel(wallet.getCurrency(), transactionsForView);
                    frmWallet.getTblTranscactions().setModel(ttm);
                }
            }
        });
            frmWallet.cbFilterAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Category filter = (Category) frmWallet.getCmbCategoryFilter().getSelectedItem();
                if(frmWallet.getCbFilter().isSelected()) {
                    for (Transaction transaction : transactionsForView) {
                        if(transaction.getCategory().equals(filter)) {
                            filteredTransactionsForView.add(transaction);
                        }
                    }
                    TransactionsTableModel ftm = new TransactionsTableModel(wallet.getCurrency(), filteredTransactionsForView);
                    frmWallet.getTblTranscactions().setModel(ftm);
                }
                else {
                    filteredTransactionsForView = new ArrayList<>();
                    TransactionsTableModel ttm = new TransactionsTableModel(wallet.getCurrency(), transactionsForView);
                    frmWallet.getTblTranscactions().setModel(ttm);
                }
            }
        });
        frmWallet.cancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frmWallet.dispose();
            }
        });
        frmWallet.addTransactionAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetForm();
                try {
                    validateForm();
                } catch (Exception ex) {
                    Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
                }
                addTransaction();
            }

            private void validateForm() throws Exception {
                String errorMessage="";
                String amountString = frmWallet.getTxtAmount().getText();
                if(amountString.isEmpty()) {
                    frmWallet.getLblAmountError().setText("Amount cannot be empty");
                    errorMessage+="Amount cannot be empty\n";
                    }
                for (int i = 0; i < amountString.length(); i++) {
                    if (!Character.isDigit(amountString.charAt(i))) {
                        frmWallet.getLblAmountError().setText("Amount should be a number!");
                        errorMessage += "Amount should be a number!";
                        }
                    }
                if(!errorMessage.isEmpty()){
                    throw new Exception (errorMessage);
                }
            }

            private void resetForm() {
                frmWallet.getLblAmountError().setText("");
            }

            private void addTransaction() {
                Double amount = Double.parseDouble(frmWallet.getTxtAmount().getText());
                if(amount<=0) {
                    frmWallet.getLblAmountError().setText("Amount should be greater than zero");
                    return;
                }
                String type;
                if(frmWallet.getRbIncome().isSelected()){
                    type = "Income";
                }
                else {
                    type = "Outflow";
                }
                Category category = (Category) frmWallet.getCmbCategory().getSelectedItem();
                Date date=new Date();
                if(frmWallet.getjFormattedTextFieldDate().getText().length()==16) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                    try {
                        date = sdf.parse(frmWallet.getjFormattedTextFieldDate().getText());
                    } catch (ParseException ex) {
                    }
                }
                System.out.println(date);
                Transaction transaction = new Transaction(amount , type, wallet, category, date);
                transactionsForView.add(transaction);
                addedTransactions.add(transaction);
                TransactionsTableModel ttm = new TransactionsTableModel(wallet.getCurrency(),transactionsForView);
                frmWallet.getTblTranscactions().setModel(ttm);
                updateBalanceLabelOnAdd(transaction);
            }

        });
        frmWallet.createCategoryAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ccc.openForm();
                frmWallet.dispose();
            }
        });
        frmWallet.deleteTransactionAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(frmWallet.getTblTranscactions().getSelectedRow()==-1) {
                    JOptionPane.showMessageDialog(frmWallet, "Please select a transaction to delete.");
                    return;
                }
                deleteTransaction();
            }

            private void deleteTransaction() {
                Transaction transactionToDelete = getEntireRow(frmWallet.getTblTranscactions().getSelectedRow());
                System.out.println(transactionToDelete.getTransactionID());
                //if(addedTransactions.contains(transactionToDelete)) {
                 //   addedTransactions.remove(transactionToDelete);
                //}                                                <---- UNECESSARY CODE
                //else {
                    deletedTransactions.add(transactionToDelete);
                    updateBalanceLabelOnDelete(transactionToDelete);
                //}
                addedTransactions.remove(transactionToDelete);
                transactionsForView.remove(transactionToDelete);
                TransactionsTableModel ttm = new TransactionsTableModel(wallet.getCurrency(),transactionsForView);
                frmWallet.getTblTranscactions().setModel(ttm);
                

            }

            private Transaction getEntireRow(int selectedRow) {
                Transaction transaction = new Transaction();
                TransactionsTableModel ttm = (TransactionsTableModel) frmWallet.getTblTranscactions().getModel();
                transaction.setTransactionID((Long) ttm.getValueAt(selectedRow, 6));
                transaction.setAmount((Double) ttm.getValueAt(selectedRow, 7));
                transaction.setTransactionType((String) ttm.getValueAt(selectedRow, 1));
                transaction.setCategory((Category) ttm.getValueAt(selectedRow, 2));
                transaction.setDate((Date) ttm.getValueAt(selectedRow, 5));
                transaction.setWallet((Wallet) ttm.getValueAt(selectedRow, 4));
                return transaction;
            }
        });
        frmWallet.saveChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(!deletedTransactions.isEmpty()){
                        CommunicationController.getInstance().deleteTransactions(deletedTransactions);
                        deletedTransactions = new ArrayList<>();
                    }
                    if(!addedTransactions.isEmpty()){
                        CommunicationController.getInstance().addTransactions(addedTransactions);
                        addedTransactions = new ArrayList<>();
                    }
                    wallet.setBalance(tempBalance);
                    CommunicationController.getInstance().updateWallet(wallet);
                    JOptionPane.showMessageDialog(frmWallet, "Changes successfully saved");
                    frmWallet.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void setUpClock() {
        ClockThread ct = new ClockThread(frmWallet.getLblClock());
        ct.start();
    }

    private void setBalanceLabel() {
        frmWallet.getLblCurrentBalance().setText("Current balance: "+wallet.getBalance());
    }

    private void updateBalanceLabelOnAdd(Transaction transaction) {
        if(transaction.getTransactionType().equals("Income")) {
            tempBalance+=transaction.getAmount();
            frmWallet.getLblCurrentBalance().setText("Current balance: "+tempBalance);
        }
        else {
            tempBalance-=transaction.getAmount();
            frmWallet.getLblCurrentBalance().setText("Current balance: "+tempBalance);
        }
    }

    private void updateBalanceLabelOnDelete(Transaction transactionToDelete) {
        if(transactionToDelete.getTransactionType().equals("Income")) {
            tempBalance-=transactionToDelete.getAmount();
            frmWallet.getLblCurrentBalance().setText("Current balance: "+tempBalance);
        }
        else {
            tempBalance+=transactionToDelete.getAmount();
            frmWallet.getLblCurrentBalance().setText("Current balance: "+tempBalance);
        }
    }
    
}
