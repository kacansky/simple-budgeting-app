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
public class ServerResponse implements Serializable{
    private Object result;
    private Exception exception;
    
    public ServerResponse(){
    }

    public ServerResponse(Object result, Exception exception) {
        this.result = result;
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public Object getResult() {
        return result;
    }
    
    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    
}
