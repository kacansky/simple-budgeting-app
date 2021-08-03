/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.components.table;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Category;

/**
 *
 * @author Dunja
 */
public class CategoriesTableModel extends AbstractTableModel{
    private ArrayList<Category> categories;
    private String[] columnNames = new String[]{"Categories"};

    public CategoriesTableModel(ArrayList<Category> categories) {
        this.categories = categories;
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
        
    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0 : return categories.get(rowIndex).getCategoryName();
            case 1: return categories.get(rowIndex).getCategoryID();
            case 2: return categories.get(rowIndex).getUser(); 
            default: return "NA";
        }
    
    }
}
