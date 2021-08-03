/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.coordinator;

import java.util.HashMap;
import java.util.Map;
import rs.ac.bg.fon.ps.controllers.CreateNewWalletController;
import rs.ac.bg.fon.ps.controllers.EditProfileController;
import rs.ac.bg.fon.ps.controllers.LoginFormController;
import rs.ac.bg.fon.ps.controllers.MainController;
import rs.ac.bg.fon.ps.controllers.ViewWalletsController;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.forms.FrmCreateNewWallet;
import rs.ac.bg.fon.ps.forms.FrmEditProfile;
import rs.ac.bg.fon.ps.forms.FrmLogin;
import rs.ac.bg.fon.ps.forms.FrmMain;
import rs.ac.bg.fon.ps.forms.FrmViewWallets;

/**
 *
 * @author Dunja
 */
public class MainCoordinator {
    
    private User currentUser;
    
    private static MainCoordinator instance;

    private final MainController mainFormController;
    private final LoginFormController loginFormController;
    
    private ViewWalletsController viewWalletsController;
    private EditProfileController editProfileController;
    private CreateNewWalletController createNewWalletController;
    
    private final Map<String, Object> params;

    private MainCoordinator() {
        this.mainFormController = new MainController(new FrmMain());
        this.loginFormController = new LoginFormController(new FrmLogin());
        this.params = new HashMap<>();
    }

    public static MainCoordinator getInstance() {
        if (instance == null) {
            instance = new MainCoordinator();
        }
        return instance;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    

    public void openLoginForm() {
        this.loginFormController.openForm();
    }

    public void openMainForm() {
        this.mainFormController.openForm();
    }

    public void logout() {
        this.mainFormController.closeForm();
    }

    public void addParam(String key, Object value) {
        params.put(key, value);
    }

    public Object getParam(String key) {
        return params.get(key);
    }


    public void openCreateNewWalletForm() {
        this.createNewWalletController = new CreateNewWalletController(new FrmCreateNewWallet());
        this.createNewWalletController.openForm();
    }

    public void openViewWalletForm() {
        this.viewWalletsController = new ViewWalletsController(new FrmViewWallets());
        this.viewWalletsController.openForm();
    }

    public void openEditSettingsForm() {
        this.editProfileController = new EditProfileController(new FrmEditProfile());
        this.editProfileController.openForm();
    }
}
