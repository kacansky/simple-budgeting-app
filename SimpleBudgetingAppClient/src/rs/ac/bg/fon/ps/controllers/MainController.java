/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.constants.Constants;
import rs.ac.bg.fon.ps.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.forms.FrmMain;

/**
 *
 * @author Dunja
 */

public class MainController {
    
    private FrmMain frmMain;
    User user;
    
    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        addActionListener();
    }
    
    public void openForm() {
        user = (User) MainCoordinator.getInstance().getParam(Constants.LOGGED_USER);
        frmMain.getLblWelcome().setText("Welcome: " + user);        
        frmMain.setTitle("Main menu");
        frmMain.setLocationRelativeTo(null);
        frmMain.setExtendedState(JFrame.NORMAL);
        frmMain.setVisible(true);
    }
    
    private void addActionListener() {
        frmMain.jmiCreateNewWalletAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openCreateNewWalletForm();
            }

            private void openCreateNewWalletForm() {
                MainCoordinator.getInstance().openCreateNewWalletForm();
            }
        });
        frmMain.jmiViewWalletsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openViewWalletForm();
                
            }

            private void openViewWalletForm() {
                MainCoordinator.getInstance().openViewWalletForm();
            }
        });
        
        
        frmMain.editProfileSettingsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openEditSettingsForm();
                
            }

            private void openEditSettingsForm() {
               MainCoordinator.getInstance().openEditSettingsForm();
            }
        });
        frmMain.logOutAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logOut(actionEvent);
            }
            
            private void logOut(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(frmMain,"Goodbye!", "Logging out...", JOptionPane.INFORMATION_MESSAGE);
                    Communication.getInstance().logout(user);
                    frmMain.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmMain, "Could not log out user.");
                    ex.printStackTrace();
                }
            }
        });
    }

    public void closeForm() {
        this.frmMain.dispose();
    }

}
