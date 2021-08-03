/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author mdjukanovic
 */
public class DataBaseConfig {
    
    private static DataBaseConfig instance;
    private Properties properties;
    
    private DataBaseConfig() throws IOException{
        this.properties = new Properties();
        this.properties.load(new FileInputStream("src/rs/ac/bg/fon/ps/resources/dbconfig.properties"));
    }
            
    public static DataBaseConfig getInstance() throws IOException {
        if(instance == null) {
            instance = new DataBaseConfig();
        }
        
        return instance;
    }
    
    public String getURL() throws Exception {
        return properties.getProperty("url");
    }
    
    public String getUsername() throws Exception {
        return this.properties.getProperty("username"); 
    }
    
    public String getPassword() throws Exception {
        return properties.getProperty("password");
    }
}
