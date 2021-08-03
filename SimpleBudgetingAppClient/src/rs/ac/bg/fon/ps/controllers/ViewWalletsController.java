/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controllers;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import rs.ac.bg.fon.ps.communication.CommunicationController;
import rs.ac.bg.fon.ps.components.table.WalletsTableModel;
import rs.ac.bg.fon.ps.constants.Constants;
import rs.ac.bg.fon.ps.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.domain.Wallet;
import rs.ac.bg.fon.ps.forms.FrmViewWallets;
import rs.ac.bg.fon.ps.forms.FrmWallet;

/**
 *
 * @author Dunja
 */
public class ViewWalletsController {
    private final FrmViewWallets frmViewWallets;
    ArrayList<Wallet> wallets;
    User user;
    
    public ViewWalletsController(FrmViewWallets frmViewWallets) {
        this.frmViewWallets = frmViewWallets;
        try {
            this.user = (User) MainCoordinator.getInstance().getParam(Constants.LOGGED_USER);
            this.wallets = (ArrayList<Wallet>) CommunicationController.getInstance().getWallets(user);
            addActionListener();
        } catch (Exception ex) {
            Logger.getLogger(ViewWalletsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openForm() {
        frmViewWallets.getTblWallets().setModel(new WalletsTableModel(wallets));
        frmViewWallets.setLocationRelativeTo(null);
        frmViewWallets.setTitle("View all wallets");
        frmViewWallets.setResizable(false);
        frmViewWallets.setVisible(true);
        prepareTable(frmViewWallets.getTblWallets());
        fillTable(wallets);
        
    }
    
    protected void fillTable(List<Wallet> wallets) {
        WalletsTableModel walletsTableModel = new WalletsTableModel(this.wallets);
        this.frmViewWallets.getTblWallets().setModel(walletsTableModel);
    }
    
    protected void prepareTable(JTable table) {
        table.getTableHeader().setBackground(Color.gray);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setGridColor(Color.gray);
    }

    private void addActionListener() {
        frmViewWallets.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTable(e);
            }
            

            private void fillTable(WindowEvent e) {
                refreshForm();
            }
            
        });
        frmViewWallets.deleteWalletAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(frmViewWallets.getTblWallets().getSelectedRow()==-1) {
                    JOptionPane.showMessageDialog(frmViewWallets, "Please select a wallet to delete.");
                    return;
                }
                if(JOptionPane.showConfirmDialog(frmViewWallets, 
                        "Are you sure you want to delete this wallet? All transactions will be lost","WARNING",
                        JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                    deleteWallet();
                }
                else {
                    return;
                }
            }

            private void deleteWallet() {
                try {
                    Wallet walletToDelete = getEntireRow(frmViewWallets.getTblWallets().getSelectedRow());
                    wallets.remove(walletToDelete);
                    WalletsTableModel wtm = new WalletsTableModel(wallets);
                    frmViewWallets.getTblWallets().setModel(wtm);
                    CommunicationController.getInstance().deleteWallet(walletToDelete);
                } catch (Exception ex) {
                    Logger.getLogger(ViewWalletsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private Wallet getEntireRow(int selectedRow) {
                Wallet w = new Wallet();
                w.setUser(user);
                w.setWalletID((Long) frmViewWallets.getTblWallets().getModel().getValueAt(selectedRow, 3));
                return w;
            }
        });
        frmViewWallets.cancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                frmViewWallets.dispose();
            }
        });
        frmViewWallets.openWalletAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(frmViewWallets.getTblWallets().getSelectedRow()==-1) {
                    JOptionPane.showMessageDialog(frmViewWallets, "Please select a wallet to delete.");
                    return;
                }
                openSelectedWallet();
            }

            private void openSelectedWallet() {
                Wallet wallet = wallets.get(frmViewWallets.getTblWallets().getSelectedRow());
                WalletController wc = new WalletController(new FrmWallet(), wallet);
                wc.openForm();
                
            }
        });
    }
    
    
    public void refreshForm() {
        fillTable(wallets);
    }
}
