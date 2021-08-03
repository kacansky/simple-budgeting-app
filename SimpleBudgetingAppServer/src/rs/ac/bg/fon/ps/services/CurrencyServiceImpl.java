/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.services;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Currency;
import rs.ac.bg.fon.ps.operations.Currency.GetAllCurrencies;

/**
 *
 * @author Dunja
 */
public class CurrencyServiceImpl implements CurrencyService {

    private static CurrencyServiceImpl instance;

    public CurrencyServiceImpl() {
    }

    public static CurrencyServiceImpl getInstance() {
        if(instance==null){
            instance = new CurrencyServiceImpl();
        }
        return instance;
    }
    
    
    @Override
    public List<Currency> getAllCurrencies() throws Exception {
        GetAllCurrencies getAllCurrencies = new GetAllCurrencies();
        getAllCurrencies.execute(new Currency());
        return getAllCurrencies.getAllCurrencies();   
    }
    
}
