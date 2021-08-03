/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Dunja
 */
public class User extends GenericEntity {
    private Long userID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public User() {
    }

    public User(String firstName, String lastName, String username, String password) {
        this.userID = new Date().getTime(); //automatically assign ID to user in form of current time and date in milliseconds
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
    
    
    @Override
    public String getTableName() {
        return "user";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "userid,first_name,last_name,username,password";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(userID).append(",").append("'").append(firstName).append("',")
                .append("'").append(lastName).append("'").append(",'")
                .append(username).append("','")
                .append(password).append("'");

        return sb.toString();
    }

    @Override
    public String getJoinConditions() {
        return "";
    }

    @Override
    public String setSelectCriteria() {
        return "userID = " + this.userID;
    }

    @Override
    public String setUpdateCriteria() {
        return "userID = " + this.userID;
    }

    @Override
    public String setDeleteCriteria() {
        return "userID = " + this.userID;
    }

    @Override
    public String getUpdateValues() {
        return "first_name="+"'"+this.firstName+"'"+",last_name="+"'"+this.lastName+"'"+",password="+"'"+this.password+"'";
    }

    @Override
    public GenericEntity setGetAllValues(ResultSet resultSet) throws Exception {
        User user = new User();
        try {
            user.setUserID(resultSet.getLong("userID"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("User cannot be loaded" +  e.getMessage());
        }
        return user;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.userID);
        hash = 67 * hash + Objects.hashCode(this.firstName);
        hash = 67 * hash + Objects.hashCode(this.lastName);
        hash = 67 * hash + Objects.hashCode(this.username);
        hash = 67 * hash + Objects.hashCode(this.password);
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
        final User other = (User) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.userID, other.userID)) {
            return false;
        }
        return true;
    }

    @Override
    public String getPrimaryKey() {
        return "userid";
    }

    @Override
    public String getPrimaryKeyValue() {
        return userID.toString();
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
