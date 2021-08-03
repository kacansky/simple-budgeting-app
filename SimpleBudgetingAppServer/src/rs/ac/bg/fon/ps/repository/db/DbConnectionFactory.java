/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db;


import rs.ac.bg.fon.ps.resources.DataBaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Dunja
 */
public class DbConnectionFactory {

    private Connection connection;
    private static DbConnectionFactory instance;

    private DbConnectionFactory() {
    }

    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            String url = DataBaseConfig.getInstance().getURL();
            String username = DataBaseConfig.getInstance().getUsername();
            String password = DataBaseConfig.getInstance().getPassword();
            
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }
}
