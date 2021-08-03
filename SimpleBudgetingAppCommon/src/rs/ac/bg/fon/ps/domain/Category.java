/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.domain;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Objects;



public class Category extends GenericEntity {
    private Long categoryID;
    private String categoryName;
    private User user;

    public Category() {
    }

    public Category(String categoryName, User user) {
        this.categoryID = new Date().getTime(); //automatically assign ID in form of current time and date in milliseconds;
        this.categoryName = categoryName;
        this.user = user;
    }

    @Override
    public String toString() {
        return this.categoryName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.categoryID);
        hash = 47 * hash + Objects.hashCode(this.categoryName);
        hash = 47 * hash + Objects.hashCode(this.user);
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
        final Category other = (Category) obj;
        if (!Objects.equals(this.categoryName, other.categoryName)) {
            return false;
        }
        return true;
    }
    
   
    
    @Override
    public String getTableName() {
        return "category";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "categoryid,name,userid";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(categoryID).append(", '").append(categoryName).append("',")
                .append(user.getUserID());

        return sb.toString();
    }

    @Override
    public String getJoinConditions() {
        return "LEFT JOIN user ON user.userid=category.userid";
    }

    @Override
    public String setSelectCriteria() {
        return "categoryid = " + this.categoryID;
    }

    @Override
    public String setUpdateCriteria() {
        return "categoryid = " + this.categoryID;
    }

    @Override
    public String setDeleteCriteria() {
        return "categoryid = " + this.categoryID;
    }

    @Override
    public String getUpdateValues() {
        return "name="+this.categoryName;

    }

    @Override
    public GenericEntity setGetAllValues(ResultSet resultSet) throws Exception {
        Category category = new Category();

        try {
            User userr = new User();
            userr.setUserID(resultSet.getLong("user.userid"));
            userr.setFirstName(resultSet.getString("user.first_name"));
            userr.setLastName(resultSet.getString("user.last_name"));
            userr.setUsername(resultSet.getString("user.username"));
            userr.setPassword(resultSet.getString("user.password"));
            category.setUser(userr);
            category.setCategoryID(resultSet.getLong("category.categoryid"));
            category.setCategoryName(resultSet.getString("category.name"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Category cannot be loaded" + e.getMessage());
        }
        return category;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getPrimaryKey() {
        return "categoryid";
    }

    @Override
    public String getPrimaryKeyValue() {
        return categoryID.toString();
    }

    @Override
    public String getGetConditions() {
        return "user.userid";
    }

    @Override
    public String getGetConditions2() {
        return "OR user.userid=44";
    }

    @Override
    public String getFilterConditions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
