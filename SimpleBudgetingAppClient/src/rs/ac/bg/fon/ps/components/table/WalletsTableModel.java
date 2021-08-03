/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.components.table;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Wallet;

/**
 *
 * @author Dunja
 */
public class WalletsTableModel extends AbstractTableModel {
    private final ArrayList<Wallet> wallets;
    private final String[] columnNames = new String[]{"Wallet name", "Balance"};
   
    public WalletsTableModel(ArrayList<Wallet> wallets) {
        this.wallets = wallets;
    }
        
    
    @Override
    public int getRowCount() {
        if(wallets==null) {
            return 0;
        }
        return wallets.size();

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
        switch(columnIndex) {
            case 0: return wallets.get(rowIndex).getWalletName();
            case 1: return wallets.get(rowIndex).getBalance()+wallets.get(rowIndex).getCurrency().getSymbol();
            case 3: return wallets.get(rowIndex).getWalletID();
            default: return "NA";
        }

    }
    
}
