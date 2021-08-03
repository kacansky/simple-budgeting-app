/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Dunja
 */
public class Transaction extends GenericEntity {
    private Long transactionID;
    private double amount;
    private String transactionType;
    private Wallet wallet;
    private Category category;
    private Date date;

    public Transaction() {
    }

    public Transaction(double amount, String transactionType, Wallet wallet, Category category, Date date) {
        this.transactionID = new Date().getTime(); //automatically assign ID in form of current time and date in milliseconds;
        this.amount = amount;
        this.transactionType = transactionType;
        this.wallet = wallet;
        this.category = category;
        this.date = date;
    }
    
    
    
    @Override
    public String getTableName() {
        return "transaction";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "transactionid,amount,transaction_type,walletid,categoryid,date";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(transactionID).append(",").append(amount).append(",")
                .append("'").append(transactionType).append("',")
                .append(wallet.getWalletID()).append(",")
                .append(category.getCategoryID()).append(",'").append(new java.sql.Date(date.getTime())).append("'");

        return sb.toString();
    }

    @Override
    public String getJoinConditions() {
        return "LEFT JOIN wallet ON wallet.walletid=transaction.walletid JOIN category ON category.categoryid = transaction.categoryid JOIN currency ON currency.currencyid = wallet.currencyid JOIN user on wallet.userid=user.userid";
    }

    @Override
    public String setSelectCriteria() {
        return "transactionid = "+this.transactionID;
    }

    @Override
    public String setUpdateCriteria() {
        return "transactionid = "+this.transactionID;
    }

    @Override
    public String setDeleteCriteria() {
        return "transaction.transactionid="+this.transactionID;
    }

    @Override
    public String getUpdateValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GenericEntity setGetAllValues(ResultSet resultSet) throws Exception {
        Transaction transaction = new Transaction();
        try {
            Currency currency = new Currency();
            currency.setCurrencyID(resultSet.getInt("currency.currencyid"));
            currency.setName(resultSet.getString("currency.name"));
            currency.setSymbol(resultSet.getString("currency.symbol"));
            
            User user = new User();
            user.setUserID(resultSet.getLong("user.userid"));
            user.setFirstName(resultSet.getString("user.first_name"));
            user.setLastName(resultSet.getString("user.last_name"));
            user.setUsername(resultSet.getString("user.username"));
            user.setPassword(resultSet.getString("user.password"));
            
           
            Wallet wallet = new Wallet();
            wallet.setWalletID(resultSet.getLong("wallet.walletid"));
            wallet.setWalletName(resultSet.getString("wallet.name"));
            wallet.setBalance(resultSet.getDouble("wallet.balance"));
            Category category = new Category();
            category.setCategoryID(resultSet.getLong("category.categoryid"));
            category.setCategoryName(resultSet.getString("category.name"));
            category.setUser(user);
            wallet.setUser(user);
            wallet.setCurrency(currency);
            
            transaction.setTransactionID(resultSet.getLong("transaction.transactionID"));
            transaction.setAmount(resultSet.getDouble("transaction.amount"));
            transaction.setTransactionType(resultSet.getString("transaction.transaction_type"));
            transaction.setDate(new Date(resultSet.getDate("transaction.date").getTime()));
            transaction.setCategory(category);
            transaction.setWallet(wallet);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Transaction cannot be loaded" +  e.getMessage());
        }
        return transaction;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        return this.transactionType + " of " + this.amount + " on date " + sdf.format(this.date);
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.transactionID);
        hash = 89 * hash + Objects.hashCode(this.amount);
        hash = 89 * hash + Objects.hashCode(this.transactionType);
        hash = 89 * hash + Objects.hashCode(this.wallet);
        hash = 89 * hash + Objects.hashCode(this.category);
        hash = 89 * hash + Objects.hashCode(this.date);
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
        final Transaction other = (Transaction) obj;
        if (!Objects.equals(this.transactionType, other.transactionType)) {
            return false;
        }
        if (!Objects.equals(this.transactionID, other.transactionID)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.wallet, other.wallet)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    @Override
    public String getPrimaryKey() {
        return "transactionid";
    }

    @Override
    public String getPrimaryKeyValue() {
        return transactionID.toString();
    }

    @Override
    public String getGetConditions() {
        return "transaction.walletid";
    }

    @Override
    public String getGetConditions2() {
        return "";
    }
    

    @Override
    public String getFilterConditions() {
        return "categoryid";
    }

    
}
