package rs.ac.bg.fon.ps.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.ResultSet;



public abstract class GenericEntity implements Serializable{
    
    public abstract String getTableName();
    
    public abstract String getColumnNamesForInsert();
    
    public abstract String getInsertValues();
    
    public abstract String getJoinConditions();

    public abstract String setSelectCriteria();

    public abstract String setUpdateCriteria();
    
    public abstract String setDeleteCriteria();
    
    public abstract String getPrimaryKey();

    public abstract String getPrimaryKeyValue();
    
    public abstract String getUpdateValues();
    
    public abstract  GenericEntity setGetAllValues(ResultSet resultSet) throws Exception;

    public abstract String getGetConditions();
    
    public abstract String getFilterConditions();
    
    public abstract  String getGetConditions2();
    
    
    
}
