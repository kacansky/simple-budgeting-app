/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.services;

/**
 *
 * @author Dunja
 */
public class ServiceRegistry {

    private ServiceRegistry() {

    }

    public static UserService getUserService() {
        return UserServiceImpl.getInstance();
    }

    public static WalletService getWalletService() {
        return WalletServiceImpl.getInstance();
    }

    public static TransactionService getTransactionService() {
        return TransactionServiceImpl.getInstance();
    }

    public static CategoryService getCategoryService() {
        return CategoryServiceImpl.getInstance();
    }

    public static CurrencyService getCurrencyService() {
        return CurrencyServiceImpl.getInstance();
    }
}
