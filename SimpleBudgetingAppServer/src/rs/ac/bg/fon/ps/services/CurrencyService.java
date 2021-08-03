/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.services;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Currency;

/**
 *
 * @author Dunja
 */
public interface CurrencyService {
    
    List<Currency> getAllCurrencies() throws Exception;

}
