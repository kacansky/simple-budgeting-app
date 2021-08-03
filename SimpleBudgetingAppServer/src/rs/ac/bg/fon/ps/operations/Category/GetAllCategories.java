/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations.Category;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.operations.AbstractGenericOperation;

/**
 *
 * @author Dunja
 */
public class GetAllCategories extends AbstractGenericOperation {

    private List<Category> allCategories;

    @Override
    protected void preconditions(Object object) throws Exception {
    }

    @Override
    protected void executeOperation(Object object) throws Exception {
        Long userid = (Long) object;
        this.allCategories = this.repository.getAllFiltered(new Category(),userid);
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }
    
}
