/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.resources.ServerConfig;
import rs.ac.bg.fon.ps.threads.HandlingClientsRequests;

/**
 *
 * @author Dunja
 */
public class Server extends Thread{
    
    private List<HandlingClientsRequests> connectedClients = new ArrayList<>();
    private boolean end = false;
    private ServerSocket serverSocket;
    
    
    @Override
    public void run() {
        this.startServer();
    }
    
    public void startServer(){
        try {
            this.serverSocket = new ServerSocket(ServerConfig.getInstance().getServerPort());
            while(!end){
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Client is connected!");
                handleClient(socket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    private void handleClient(Socket socket) throws Exception{
        HandlingClientsRequests handlingClientsRequests = new HandlingClientsRequests(socket);
        this.connectedClients.add(handlingClientsRequests);
        handlingClientsRequests.start();
    }

    public void stopServer() throws IOException {
        for(HandlingClientsRequests handlingClientsRequests : this.connectedClients) {
            handlingClientsRequests.serverStopped();
        }
        this.end = true;
        serverSocket.close();
    }
}
