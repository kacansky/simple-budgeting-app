/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Objects;




public class Currency extends GenericEntity {
    private int currencyID;
    private String name;
    private String symbol;

    public Currency() {
    }

    public Currency(int currencyID, String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.currencyID = currencyID;
    }
    
    @Override
    public String getTableName() {
        return "currency";
    }

    @Override
    public String getColumnNamesForInsert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInsertValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getJoinConditions() {
        return "";
    }

    @Override
    public String setSelectCriteria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String setUpdateCriteria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String setDeleteCriteria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUpdateValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GenericEntity setGetAllValues(ResultSet resultSet) throws Exception {
        Currency currency = new Currency();
        try {
            
            currency.setCurrencyID(resultSet.getInt("currency.currencyid"));
            currency.setName(resultSet.getString("currency.name"));
            currency.setSymbol(resultSet.getString("currency.symbol"));
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Currency cannot be loaded" +  e.getMessage());
        }
        return currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.symbol);
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
        final Currency other = (Currency) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        return true;
    }

    public int getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(int currencyID) {
        this.currencyID = currencyID;
    }

    @Override
    public String toString() {
        return this.name + '(' + this.symbol + ')';
    }

    @Override
    public String getPrimaryKey() {
        return "currencyid";
    }

    @Override
    public String getPrimaryKeyValue() {
        return String.valueOf(currencyID);
    }

    @Override
    public String getGetConditions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
