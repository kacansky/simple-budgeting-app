/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Dunja
 */
public class Wallet extends GenericEntity {
    private Long walletID;
    private String walletName;
    private double balance;
    private User user;
    private Currency currency;

    public Wallet() {
    }

    public Wallet(String walletName, double balance, User user, Currency currency) {
        this.walletID = new Date().getTime(); //automatically assign ID in form of current time and date in milliseconds;
        this.walletName = walletName;
        this.balance = balance;
        this.user = user;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Wallet: " + "walletName=" + walletName + ", balance= " + balance+currency.getSymbol();
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.walletID);
        hash = 31 * hash + Objects.hashCode(this.walletName);
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.balance) ^ (Double.doubleToLongBits(this.balance) >>> 32));
        hash = 31 * hash + Objects.hashCode(this.user);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Wallet other = (Wallet) obj;
        if (!Objects.equals(this.walletID, other.walletID)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }
    
    
    @Override
    public String getTableName() {
        return "wallet";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "walletid, name, balance, currencyid, userid";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(walletID).append(",").append("'").append(walletName).append("',")
                .append(balance).append(",").append(currency.getCurrencyID()).append(",").append(user.getUserID());

        return sb.toString();

    }

    @Override
    public String getJoinConditions() {
        return "LEFT JOIN user on user.userid = wallet.userid JOIN currency on currency.currencyid = wallet.currencyid";
    }

    @Override
    public String setSelectCriteria() {
          return "walletid = "+this.walletID;
    }

    @Override
    public String setUpdateCriteria() {
        return "wallet.walletid = "+this.walletID;
    }

    @Override
    public String setDeleteCriteria() {
        return "walletid = "+this.walletID + " AND userid="+this.user.getUserID();
    }

    @Override
    public String getUpdateValues() {
        return "balance="+this.balance;
    }

    @Override
    public GenericEntity setGetAllValues(ResultSet resultSet) throws Exception {
        Wallet wallet = new Wallet();
        System.out.println("HELOOO");
        try {
            wallet.setWalletID(resultSet.getLong("walletID"));
            wallet.setWalletName(resultSet.getString("name"));
            wallet.setBalance(resultSet.getDouble("balance"));
            Currency currency = new Currency();
            currency.setName(resultSet.getString("currency.name"));
            currency.setSymbol(resultSet.getString("currency.symbol"));
            wallet.setCurrency(currency);
            User user = new User();
            user.setUserID(resultSet.getLong("user.userid"));
            user.setFirstName(resultSet.getString("user.first_name"));
            user.setLastName(resultSet.getString("user.last_name"));
            user.setUsername(resultSet.getString("user.username"));
            user.setPassword(resultSet.getString("user.password"));
            wallet.setUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Wallet cannot be loaded" +  e.getMessage());
        }
        return wallet;
    }

    public Long getWalletID() {
        return walletID;
    }

    public void setWalletID(Long walletID) {
        this.walletID = walletID;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String getPrimaryKey() {
        return "walletid";
    }

    @Override
    public String getPrimaryKeyValue() {
        return walletID.toString();
    }

    @Override
    public String getGetConditions() {
        return "wallet.userid";
    }

    @Override
    public String getFilterConditions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getGetConditions2() {
        return "";
    }

    
    
}
