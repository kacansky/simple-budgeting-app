/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.services;

import java.util.List;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.domain.Wallet;
import rs.ac.bg.fon.ps.operations.Wallet.DeleteWallet;
import rs.ac.bg.fon.ps.operations.Wallet.GetAllWallets;
import rs.ac.bg.fon.ps.operations.Wallet.InsertWallet;
import rs.ac.bg.fon.ps.operations.Wallet.UpdateWallet;

/**
 *
 * @author Dunja
 */
public class WalletServiceImpl implements WalletService {

    
    private static WalletServiceImpl instance;

    private WalletServiceImpl() {
    }

    public static WalletService getInstance() {
        if (instance == null) {
            instance = new WalletServiceImpl();
        }
        return instance;
    }
    
    @Override
    public List<Wallet> getAllWallets(User user) throws Exception {
        GetAllWallets getAllWallets = new GetAllWallets();
        getAllWallets.execute(user.getUserID());
        return getAllWallets.getAllWallets();
    }

    @Override
    public void updateWallet(Wallet wallet) throws Exception {
        UpdateWallet updateWallet = new UpdateWallet();
        updateWallet.execute(wallet);
    }

    @Override
    public void createWallet(Wallet wallet) throws Exception {
        InsertWallet insertWallet = new InsertWallet();
        insertWallet.execute(wallet);
    }

    @Override
    public void deleteWallet(Wallet wallet) throws Exception {
        DeleteWallet deleteWallet = new DeleteWallet();
        deleteWallet.execute(wallet);
    }
    
}
