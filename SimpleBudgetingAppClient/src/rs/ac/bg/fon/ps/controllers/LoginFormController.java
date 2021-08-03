/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.CommunicationController;
import rs.ac.bg.fon.ps.constants.Constants;
import rs.ac.bg.fon.ps.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.forms.FrmLogin;
import rs.ac.bg.fon.ps.forms.FrmSignUp;
import rs.ac.bg.fon.ps.help.HelpData;

/**
 *
 * @author mdjukanovic
 */
public class LoginFormController {

    private final FrmLogin loginForm;

    public LoginFormController(FrmLogin frmLogin) {
        this.loginForm = frmLogin;
        this.addActionListeners();
    }

    private void addActionListeners() {
        this.loginForm.getBtnSignUp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupUser();
            }
            private void signupUser() {
                SignUpController suc = new SignUpController(new FrmSignUp());
                suc.openForm();
            }
            
        });
        this.loginForm.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }

            private void loginUser() {
                this.resetForm();
                try {
                    String username = loginForm.getTxtUsername().getText();
                    String password = String.valueOf(loginForm.getTxtPassword().getPassword());

                    validateForm(username, password);

                    User user = CommunicationController.getInstance().login(username, password);
                    JOptionPane.showMessageDialog(loginForm,
                            "Welcome " + user.getFirstName() + " " + user.getLastName(),
                            "LOGGED IN", JOptionPane.INFORMATION_MESSAGE);
                    
                    loginForm.dispose();
                    HelpData.loggedUser = user;
                    MainCoordinator.getInstance().addParam(Constants.LOGGED_USER, user);
                    MainCoordinator.getInstance().openMainForm();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(loginForm, e.getMessage(), "LOGIN ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void resetForm() {
                loginForm.getLblError().setText("");
            }

            private void validateForm(String username, String password) throws Exception {
                String errorMessage = "";

                if (username.isEmpty()) {
                    loginForm.getLblError().setText("Username can not be empty!");
                    errorMessage += "Username cannot be empty!\n";
                }
                if (password.isEmpty()) {
                    loginForm.getLblError().setText("Password can not be empty!");
                    errorMessage += "Password cannot be empty!\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }
        });
    }

    public void openForm() {
        this.loginForm.setLocationRelativeTo(null);
        this.loginForm.setVisible(true);
    }

}
