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
import rs.ac.bg.fon.ps.forms.FrmEditProfile;

/**
 *
 * @author Dunja
 */
public class EditProfileController {
    private FrmEditProfile frmEditProfile;
    User user = (User) MainCoordinator.getInstance().getParam(Constants.LOGGED_USER);
    public EditProfileController(FrmEditProfile frmEditProfile) {
        this.frmEditProfile = frmEditProfile;
        addActionListener();
    }

    public void openForm() {
        frmEditProfile.setLocationRelativeTo(null);
        frmEditProfile.setTitle("Edit profile settings");
        frmEditProfile.setResizable(true);
        frmEditProfile.setVisible(true);
        frmEditProfile.getTxtFirstName().setText(user.getFirstName());
        frmEditProfile.getTxtLastName().setText(user.getLastName());
        frmEditProfile.getLblUsername().setText("Username: "+user.getUsername());
        
    }

    private void addActionListener() {
        frmEditProfile.cancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frmEditProfile.dispose();
                MainCoordinator.getInstance().openMainForm();
            }
        });
        frmEditProfile.changePasswordAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ChangePasswordController cpp = new ChangePasswordController(new FrmChangePassword());
                cpp.openForm();
            }
        });
        frmEditProfile.saveChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    validateForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmEditProfile, "Cannot update account settings.");
                }
                updateUser();
                frmEditProfile.dispose();
        }

            private void validateForm() throws Exception {
                String errorMessage = "";
                String first_name = frmEditProfile.getTxtFirstName().getText();
                String last_name = frmEditProfile.getTxtLastName().getText();
                if (first_name.isEmpty()) {
                    frmEditProfile.getLblError().setText("First name cannot be empty");
                    errorMessage += "Username field cannot be empty\n";
                }
                if (last_name.isEmpty()) {
                    frmEditProfile.getLblError().setText("Last name cannot be empty");
                    errorMessage += "Password field cannot be empty\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
                
            }

            private void updateUser() {
                try {
                    String first_name = frmEditProfile.getTxtFirstName().getText();
                    String last_name = frmEditProfile.getTxtLastName().getText();
                    User user = (User) MainCoordinator.getInstance().getParam(Constants.LOGGED_USER);
                    user.setFirstName(first_name);
                    user.setLastName(last_name);
                    Communication.getInstance().updateUser(user);
                } catch (Exception ex) {
                    Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
    }
    
}
