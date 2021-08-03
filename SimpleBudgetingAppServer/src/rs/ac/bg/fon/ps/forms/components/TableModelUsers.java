/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.forms.components;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import rs.ac.bg.fon.ps.domain.User;

/**
 *
 * @author mdjukanovic
 */
public class TableModelUsers extends AbstractTableModel {

    private List<User> users;
    private String[] columnNames = new String[]{"Username", "Full name"};
    private Class<?>[] classes = new Class<?>[]{String.class, String.class};

    public TableModelUsers(List<User> users) {
        super();
        this.users = users;
    }

    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return this.classes[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public int getRowCount() {
        return this.users.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = this.users.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return user.getUsername();
            case 1:
                return user.getFirstName()+" "+user.getLastName();
            default:
                return "NA";
        }
    }

}
