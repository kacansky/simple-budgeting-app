/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Dunja
 */
public class Receiver implements Serializable{
    private Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }

    public Object receive() throws Exception{
        try {
            if(socket.isClosed()) {
                System.out.println("Socket closed bro");
            }
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SocketException("Error while receiving response object!" + e.getMessage());
        }
    }
    
}
