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
public class ServerConfig {
    
    private static ServerConfig instance;
    private Properties properties;
    
    private ServerConfig() throws IOException {
        this.properties = new Properties();
        this.properties.load(new FileInputStream("src/rs/ac/bg/fon/ps/resources/serverconfig.properties"));
    }
    
    public static ServerConfig getInstance() throws IOException {
        if(instance == null) {
            instance = new ServerConfig();
        }
        return instance;
    }
    
    public int getServerPort() {
        return Integer.parseInt(this.properties.getProperty("port"));
    }
    
    public String getHost() {
        return this.properties.getProperty("host");
    }
}
