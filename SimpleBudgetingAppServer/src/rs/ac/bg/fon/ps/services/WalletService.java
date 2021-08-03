/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.services;

import java.util.List;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.domain.Wallet;

/**
 *
 * @author Dunja
 */
public interface WalletService {
    
    List<Wallet> getAllWallets(User user) throws Exception;

    void updateWallet(Wallet wallet) throws Exception;

    void createWallet(Wallet wallet) throws Exception;
    
    void deleteWallet(Wallet wallet) throws Exception;
}
