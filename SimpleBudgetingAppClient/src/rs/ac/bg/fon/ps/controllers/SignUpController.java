/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.forms.FrmLogin;
import rs.ac.bg.fon.ps.forms.FrmSignUp;

/**
 *
 * @author Dunja
 */
public class SignUpController {
    private FrmSignUp frmSignUp;

    public SignUpController(FrmSignUp frmSignUp) {
        this.frmSignUp = frmSignUp;
        addActionListener();
    }
    
    
    public void openForm() {
        frmSignUp.setLocationRelativeTo(null);
        frmSignUp.setTitle("Simple budgeting app - Sign up");
        frmSignUp.setResizable(false);
        frmSignUp.setVisible(true);
    
    }

    private void addActionListener() {
        frmSignUp.cancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frmSignUp.dispose();
            }
        });
        frmSignUp.signUpAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    validateForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmSignUp, "Cannot create account.");
                    return;
                }
                try {
                signUpUser();
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmSignUp, ex);
                }
                //resetForm();
            }

            private void signUpUser() {
                String firstName = frmSignUp.getTxtFirstName().getText();
                String lastName = frmSignUp.getTxtLastName().getText();
                String username = frmSignUp.getTxtUsername().getText();
                String password = String.copyValueOf(frmSignUp.getTxtPassword().getPassword());
                User user = new User(firstName, lastName, username, password);
                try {
                    Communication.getInstance().saveNewUser(user);
                    LoginFormController lic = new LoginFormController(new FrmLogin());
                    lic.openForm();
                    JOptionPane.showMessageDialog(frmSignUp, "Account successfully created.");
                    frmSignUp.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmSignUp, "Cannot save user.\n"+ex.getMessage());
                }

            }

            private void validateForm() throws Exception {
                String firstName = frmSignUp.getTxtFirstName().getText();
                String lastName = frmSignUp.getTxtLastName().getText();
                String username = frmSignUp.getTxtUsername().getText();
                String password = String.copyValueOf(frmSignUp.getTxtPassword().getPassword());
                String password1 = String.copyValueOf(frmSignUp.getTxtPasswordRepeat().getPassword());
                String errorMessage = "";
                
                if(firstName.isEmpty()) {
                    frmSignUp.getLblUsernameError().setText("First name field cannot be empty");
                    errorMessage += "First name field cannot be empty\n";
                }
                
                if(firstName.isEmpty()) {
                    frmSignUp.getLblUsernameError().setText("Last name field cannot be empty");
                    errorMessage += "Last name field cannot be empty\n";
                }
                
                if (username.isEmpty()) {
                    frmSignUp.getLblUsernameError().setText("Username field cannot be empty");
                    errorMessage += "Username field cannot be empty\n";
                }
                if (password.isEmpty()) {
                    frmSignUp.getLblPasswordError().setText("Password field cannot be empty");
                    errorMessage += "Password field cannot be empty\n";
                }
                if (!password.equals(password1)) {
                    frmSignUp.getLblPasswordRepeatError().setText("Passwords do not match");
                    errorMessage += "Passwords do not match\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }

            private void resetForm() {
                frmSignUp.getTxtFirstName().setText("");
                frmSignUp.getTxtLastName().setText("");
                frmSignUp.getTxtUsername().setText("");
                frmSignUp.getTxtPassword().setText("");
                frmSignUp.getTxtPasswordRepeat().setText("");
            }
        });
    }
}