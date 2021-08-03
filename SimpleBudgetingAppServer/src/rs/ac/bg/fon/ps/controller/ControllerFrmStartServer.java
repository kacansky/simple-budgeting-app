/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controller;

import rs.ac.bg.fon.ps.forms.FrmStartServer;
import rs.ac.bg.fon.ps.forms.components.TableModelUsers;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.resources.DataBaseConfig;
import rs.ac.bg.fon.ps.resources.ServerConfig;

/**
 *
 * @author mdjukanovic
 */
public class ControllerFrmStartServer {

    private final FrmStartServer frmStartServer;
    private List<User> users = new ArrayList<>();

    public ControllerFrmStartServer(FrmStartServer frmStartServer) {
        this.frmStartServer = frmStartServer;
        this.addActionListeners();
    }

    private void addActionListeners() {
        this.frmStartServer.getBtnStartServer().addActionListener(e -> {
            startServerView();
            Controller.getInstance().startServer();
        });

        this.frmStartServer.getBtnStopServer().addActionListener(e -> {
            stopServerView();
            try {
                Controller.getInstance().stopServer();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frmStartServer, "Unable to stop server!");
                Logger.getLogger(ControllerFrmStartServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        frmStartServer.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTable(e);
            }
            

            private void fillTable(WindowEvent e) {
                refreshTable();
            }
            
        });
    }

    public void openForm() {
        this.frmStartServer.setVisible(true);
        this.prepareForm();
    }

    private void stopServerView() {
        this.frmStartServer.getLblServerStatus().setText("Server is down");
        this.frmStartServer.getLblServerStatus().setForeground(Color.red);
        this.frmStartServer.getBtnStartServer().setEnabled(true);
        this.frmStartServer.getBtnStopServer().setEnabled(false);
    }

    private void startServerView() {
        this.frmStartServer.getLblServerStatus().setText("Server is up");
        this.frmStartServer.getLblServerStatus().setForeground(Color.GREEN);
        this.frmStartServer.getBtnStartServer().setEnabled(false);
        this.frmStartServer.getBtnStopServer().setEnabled(true);
    }

    private void prepareTable() {
        TableModelUsers tableModelUsers = new TableModelUsers(users);
        this.frmStartServer.getTableUsers().setModel(tableModelUsers);
    }

    public void refreshTable() {
        this.prepareTable();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void prepareViewTable() {
        this.frmStartServer.getTableUsers().getTableHeader().setBackground(Color.gray);
        this.frmStartServer.getTableUsers().setShowHorizontalLines(true);
        this.frmStartServer.getTableUsers().setShowVerticalLines(true);
        this.frmStartServer.getTableUsers().setGridColor(Color.gray);
    }
    
    private void prepareForm(){
        this.stopServerView();
        this.prepareTable();
        this.prepareViewTable();
        try {
            int port = ServerConfig.getInstance().getServerPort();
            this.frmStartServer.getTxtPort().setEnabled(false);
            this.frmStartServer.getTxtPort().setText(port + "");
            
            String urlDB = DataBaseConfig.getInstance().getURL();
            String userDB = DataBaseConfig.getInstance().getUsername();
            String passwordDB = DataBaseConfig.getInstance().getPassword();
            
            this.frmStartServer.getTxtPassword().setEnabled(false);
            this.frmStartServer.getTxtUrl().setEnabled(false);
            this.frmStartServer.getTxtUser().setEnabled(false);
            this.frmStartServer.getTxtPassword().setText(passwordDB);
            this.frmStartServer.getTxtUrl().setText(urlDB);
            this.frmStartServer.getTxtUser().setText(userDB);
            
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
}
