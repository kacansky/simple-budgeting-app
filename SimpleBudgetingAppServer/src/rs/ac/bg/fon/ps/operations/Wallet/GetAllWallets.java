/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations.Wallet;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Wallet;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author Dunja
 */
public class GetAllWallets extends AbstractGenericOperation {

    private List<Wallet> allWallets;

    
    @Override
    protected void preconditions(Object object) throws Exception {

    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        this.allWallets = this.repository.getAllFiltered(new Wallet(),(Long) object);
    }

    public List<Wallet> getAllWallets() {
        return allWallets;
    }
    
}
