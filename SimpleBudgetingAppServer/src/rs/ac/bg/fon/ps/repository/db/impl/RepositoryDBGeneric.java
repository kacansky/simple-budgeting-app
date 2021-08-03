/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db.impl;

import rs.ac.bg.fon.ps.repository.db.DbConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.IDbRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.GenericEntity;

/**
 *
 * @author Dunja
 */


public class RepositoryDBGeneric implements IDbRepository<GenericEntity, Long> {

    @Override
    public Long insert(GenericEntity genericEntity) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("INSERT INTO ").append(genericEntity.getTableName()).append(" (").
                    append(genericEntity.getColumnNamesForInsert()).append(" )").append(" VALUES (").
                    append(genericEntity.getInsertValues()).append(")");

            String query = stringBuilder.toString();
            System.out.println(query);
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Object creation failure, no ID obtained.");
                }
            } catch (Exception e) {
            }

            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SQLException("ERROR: Object cannot be created!");
        }

        return null;
    }

    @Override
    public void update(GenericEntity genericEntity) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UPDATE ").append(genericEntity.getTableName()).append(" SET ").
                    append(genericEntity.getUpdateValues()).append(" WHERE ").
                    append(genericEntity.setUpdateCriteria());
            String query = stringBuilder.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("ERROR: Cannot update object!");
        }
    }

    @Override
    public void delete(GenericEntity genericEntity) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DELETE FROM ").append(genericEntity.getTableName()).append(" WHERE ").
                    append(genericEntity.setDeleteCriteria());

            String query = stringBuilder.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("ERROR: Cannot delete object");
        }
    }

    @Override
    public List<GenericEntity> getAll(GenericEntity genericEntity) throws Exception {
        List<GenericEntity> listToReturn = new ArrayList<>();
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT ").append("*").append(" FROM ").append(genericEntity.getTableName()).
                    append(" ").append(genericEntity.getJoinConditions());

            String query = stringBuilder.toString();
            System.out.println(query);
            Statement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                genericEntity = genericEntity.setGetAllValues(resultSet);
                listToReturn.add(genericEntity);
            }

            resultSet.close();
            statement.close();
            return listToReturn;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("ERROR: Cannot load objects!");
        }
    }

   

    @Override
    public List<GenericEntity> getAllFiltered(GenericEntity genericEntity, Long key) throws Exception {
        List<GenericEntity> listToReturn = new ArrayList<>();
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT ").append("*").append(" FROM ").append(genericEntity.getTableName()).
                    append(" ").append(genericEntity.getJoinConditions()).append(" WHERE ").append(genericEntity.getGetConditions()).append("=").append(key).append(" ").append(genericEntity.getGetConditions2());

            String query = stringBuilder.toString();
            System.out.println(query);
            Statement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                genericEntity = genericEntity.setGetAllValues(resultSet);
                listToReturn.add(genericEntity);
            }

            resultSet.close();
            statement.close();
            return listToReturn;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("ERROR: Cannot obtain data!");
        }
    }

}
