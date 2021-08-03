/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations.Category;

import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author Dunja
 */
public class UpdateCategory extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object object) throws Exception {
        if (!(object instanceof Category)) {
            throw new Exception("Invalid category data");
        }
        
        Category category = (Category) object;
        if (category.getCategoryID()== null) {
            throw new IllegalStateException("ID cannot be null");
        }
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        this.repository.update((Category) object);
    }
    
}
