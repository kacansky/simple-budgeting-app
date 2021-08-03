/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controllers;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.communication.CommunicationController;
import rs.ac.bg.fon.ps.constants.Constants;
import rs.ac.bg.fon.ps.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.domain.Currency;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.domain.Wallet;
import rs.ac.bg.fon.ps.forms.FrmCreateNewWallet;
import rs.ac.bg.fon.ps.help.HelpData;

/**
 *
 * @author Dunja
 */
public class CreateNewWalletController {
    private final FrmCreateNewWallet frmCreateNewWallet;
    ArrayList<Currency> currencies;
    User user;
    
    public CreateNewWalletController(FrmCreateNewWallet frmCreateNewWallet) {
        this.frmCreateNewWallet= frmCreateNewWallet;
        addActionListener();
        this.user = HelpData.loggedUser;
    }
    

    public void openForm() {
        frmCreateNewWallet.setLocationRelativeTo(null);
        frmCreateNewWallet.setTitle("Create a new wallet");
        frmCreateNewWallet.setResizable(false);
        prepareView();
        frmCreateNewWallet.setVisible(true);

    }

    private void prepareView() {
        try {
            user = (User) MainCoordinator.getInstance().getParam(Constants.LOGGED_USER);
            //System.out.println(user);
            fillCurrencyComboBox();
            frmCreateNewWallet.getTxtWalletName().setText("");
            frmCreateNewWallet.getTxtStartBalance().setText("");
        } catch (Exception ex) {
            Logger.getLogger(CreateNewWalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillCurrencyComboBox() {
        try{
            currencies = (ArrayList<Currency>) CommunicationController.getInstance().getCurrencies();
            this.frmCreateNewWallet.getCmbCurrency().setModel(new DefaultComboBoxModel<>(currencies.toArray()));
        }
        catch (Exception ex) {
            //Logger.getLogger(CreateNewWalletController.class
              //      .getName()).log(Level.SEVERE, null, ex);
              fillCurrencyComboBox();
        }
    }   
    
    private void addActionListener() {
        frmCreateNewWallet.cancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frmCreateNewWallet.dispose();
            }
        });
        frmCreateNewWallet.createNewWalletAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    resetForm();
                    validateForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmCreateNewWallet, "Could not create wallet.");
                }
                addNewWallet();
                frmCreateNewWallet.dispose();
                }

            private void validateForm() throws Exception {
                String errorMessage = "";
                String walletName = frmCreateNewWallet.getTxtWalletName().getText();
                String startBalanceString = frmCreateNewWallet.getTxtStartBalance().getText();
                if(walletName.isEmpty()) {
                    frmCreateNewWallet.getLblWalletNameError().setText("Wallet name cannot be empty");
                    errorMessage+="Wallet name cannot be empty\n";
                    }
                if(startBalanceString.isEmpty()) {
                    frmCreateNewWallet.getLblStartBalanceError().setText("Start balance cannot be empty");
                    errorMessage+="Start balance cannot be empty\n";
                    }
                for (int i = 0; i < startBalanceString.length(); i++) {
                    if (!Character.isDigit(startBalanceString.charAt(i))) {
                        frmCreateNewWallet.getLblStartBalanceError().setText("Start balance should be a number!");
                        errorMessage += "Start balance should be a number!";
                       
                        }
                    }
                if(!errorMessage.isEmpty()){
                    throw new Exception(errorMessage);
                }
            }

            private void resetForm() {
                frmCreateNewWallet.getLblWalletNameError().setText("");
                frmCreateNewWallet.getLblStartBalanceError().setText("");
            }

            private void addNewWallet() {
                String walletName = frmCreateNewWallet.getTxtWalletName().getText();
                String startBalanceString = frmCreateNewWallet.getTxtStartBalance().getText();
                double startBalance = Double.parseDouble(startBalanceString);
                Currency currency = (Currency) frmCreateNewWallet.getCmbCurrency().getSelectedItem();
                System.out.println(user);
                System.out.println(currency);
                Wallet wallet = new Wallet(walletName, startBalance, user, currency);
                try {
                    CommunicationController.getInstance().saveNewWallet(wallet);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmCreateNewWallet,"Could not save wallet");
                }
            }
        });
    }
}
