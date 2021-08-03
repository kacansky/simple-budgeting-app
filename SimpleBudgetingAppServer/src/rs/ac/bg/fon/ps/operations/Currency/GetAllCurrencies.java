/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations.Currency;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Currency;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author Dunja
 */
public class GetAllCurrencies extends AbstractGenericOperation {

    List<Currency> allCurrencies;
    
    @Override
    protected void preconditions(Object object) throws Exception {
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        this.allCurrencies = this.repository.getAll(new Currency());
    }

    public List<Currency> getAllCurrencies() {
        return allCurrencies;
    }
    
}
