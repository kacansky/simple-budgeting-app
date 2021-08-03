/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.services;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.domain.Transaction;
import rs.ac.bg.fon.ps.domain.User;

/**
 *
 * @author Dunja
 */
public interface CategoryService {
    
    List<Category> getAllCategories(Long user) throws Exception;
    
    void createCategory(Category category) throws Exception;
    
    void deleteCategory(Category category) throws Exception;
    
    void updateCategory(Category category) throws Exception;

    public void saveCategories(ArrayList<Category> categoriesToSave);

    public void deleteCategories(ArrayList<Category> categoriesToDelete);

    
}
