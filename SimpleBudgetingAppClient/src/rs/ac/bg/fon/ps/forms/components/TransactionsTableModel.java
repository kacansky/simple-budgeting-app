/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.forms.components;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.domain.Transaction;

/**
 *
 * @author Dunja
 */
public class TransactionsTableModel extends AbstractTableModel {
    private ArrayList<Transaction> transactions;
    private String[] columnNames = new String[]{"Amount", "Type", "Category", "Date"};

    public TransactionsTableModel(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        if(transactions==null) {
            return 0;
        }
        return transactions.size();
    }
    
    @Override
    public String getColumnName(int column) {
        if (column > columnNames.length) {
            return "NA";
        }
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        switch(columnIndex) {
            case 0: return transactions.get(rowIndex).getAmount();
            case 1: return transactions.get(rowIndex).getTransactionType();
            case 2: return transactions.get(rowIndex).getCategory();
            case 3: return sdf.format(transactions.get(rowIndex).getDate());
            case 4: return transactions.get(rowIndex).getWallet();
            case 5: return transactions.get(rowIndex).getDate();
            case 6: return transactions.get(rowIndex).getTransactionID();
            default: return "NA";
        }
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    
    
}
