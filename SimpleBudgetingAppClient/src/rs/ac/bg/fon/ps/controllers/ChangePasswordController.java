/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.constants.Constants;
import rs.ac.bg.fon.ps.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.forms.FrmChangePassword;
import rs.ac.bg.fon.ps.help.HelpData;

/**
 *
 * @author Dunja
 */
public class ChangePasswordController {
    private FrmChangePassword frmChangePassword;
    private User user;

    public ChangePasswordController(FrmChangePassword frmChangePassword) {
        this.frmChangePassword = frmChangePassword;
        addActionListener();
        this.user = (User) MainCoordinator.getInstance().getParam(Constants.LOGGED_USER);
    }

    private void addActionListener() {
        frmChangePassword.cancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmChangePassword.dispose();
                MainCoordinator.getInstance().openEditSettingsForm();
            }
        });
        frmChangePassword.SavePasswordAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    validateForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmChangePassword, ex.getMessage());
                    return;
                }
                updatePassword();
                frmChangePassword.dispose();
            }

            private void validateForm() throws Exception {
                if(frmChangePassword.getTxtCurrentPassword().getText().isEmpty() || frmChangePassword.getTxtNewPassword().getText().isEmpty() || frmChangePassword.getTxtNewPasswordRepeat().getText().isEmpty()){
                    frmChangePassword.getLblError().setText("Some fields are empty");
                    throw new Exception("Cannot change password. Some fields are empty.");
                }
                if(!frmChangePassword.getTxtCurrentPassword().getText().equals(user.getPassword())) {
                    frmChangePassword.getLblError().setText("That is not your current password");
                    throw new Exception("Cannot change password. That is not your current password.");
                }
                else {
                    if(!frmChangePassword.getTxtNewPassword().getText().equals(frmChangePassword.getTxtNewPasswordRepeat().getText())) {
                        frmChangePassword.getLblError().setText("New password and repeated new password do not match");
                        throw new Exception("Cannot change password. New password and repeated new password do not match.");
                    }
                }

            }

            private void updatePassword() {
                try {
                    user.setPassword(frmChangePassword.getTxtNewPassword().getText());
                    Communication.getInstance().updateUser(user);
                    JOptionPane.showMessageDialog(frmChangePassword, "Password successfully changed");
                } catch (Exception ex) {
                    Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
    }

    public void openForm() {
        resetForm();
        frmChangePassword.setLocationRelativeTo(null);
        frmChangePassword.setTitle("Simple budgeting app - Change password");
        frmChangePassword.setResizable(false);
        frmChangePassword.setVisible(true);
    }

    private void resetForm() {
        frmChangePassword.getTxtNewPassword().setText("");
        frmChangePassword.getTxtNewPasswordRepeat().setText("");
        frmChangePassword.getTxtCurrentPassword().setText("");
    }
    
    
}
