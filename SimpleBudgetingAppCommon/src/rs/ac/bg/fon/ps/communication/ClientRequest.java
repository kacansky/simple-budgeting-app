/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.io.Serializable;

/**
 *
 * @author Dunja
 */
public class ClientRequest implements Serializable{
    private int operation;
    private Object argument;

    public ClientRequest() {
    }

    public ClientRequest(int operation, Object argument) {
        this.operation = operation;
        this.argument = argument;
    }

    public Object getArgument() {
        return argument;
    }

    public int getOperation() {
        return operation;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
    
    
}
