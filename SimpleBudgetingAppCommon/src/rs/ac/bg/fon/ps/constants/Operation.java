/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.constants;

import java.io.Serializable;



public class Operation implements Serializable {


    private Operation() {
        
    }
    
    public static final int LOGIN = 1;
    public static final int GET_CATEGORIES_FOR_USER = 2;
    public static final int GET_ALL_CURRENCIES = 3;
    public static final int LOGOUT = 4;
    public static final int SAVE_NEW_USER = 5;
    public static final int UPDATE_PASSWORD = 6;
    public static final int SAVE_NEW_WALLET = 7;
    public static final int GET_ALL_TRANSACTIONS = 8;
    public static final int GET_ALL_WALLETS = 9;
    public static final int UPDATE_USER_SETTINGS = 10;
    public static final int DELETE_TRANSACTIONS = 11;
    public static final int DELETE_WALLET = 12;
    public static final int UPDATE_WALLET = 13;
    public static final int ADD_TRANSACTIONS = 14;
    public static final int SAVE_CATEGORIES = 15;
    public static final int DELETE_CATEGORIES = 16;
    
    
    
    
}
