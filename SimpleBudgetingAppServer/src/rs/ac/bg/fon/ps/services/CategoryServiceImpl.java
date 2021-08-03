/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.operations.Category.DeleteCategory;
import rs.ac.bg.fon.ps.operations.Category.GetAllCategories;
import rs.ac.bg.fon.ps.operations.Category.InsertCategory;
import rs.ac.bg.fon.ps.operations.Category.UpdateCategory;

/**
 *
 * @author Dunja
 */
public class CategoryServiceImpl implements CategoryService {

    private static CategoryServiceImpl instance;

    private CategoryServiceImpl() {
    }

    public static CategoryServiceImpl getInstance() {
        if (instance == null) {
            instance = new CategoryServiceImpl();
        }
        return instance;
    }
    
    @Override
    public List<Category> getAllCategories(Long userid) throws Exception {
        GetAllCategories getAllCategories = new GetAllCategories();
        getAllCategories.execute(userid);
        return getAllCategories.getAllCategories();
    }

    @Override
    public void createCategory(Category category) throws Exception {
        InsertCategory insertCategory = new InsertCategory();
        insertCategory.execute(category);
    }

    @Override
    public void deleteCategory(Category category) throws Exception {
        DeleteCategory deleteCategory = new DeleteCategory();
        deleteCategory.execute(category);
    }

    @Override
    public void updateCategory(Category category) throws Exception {
        UpdateCategory updateCategory = new UpdateCategory();
        updateCategory.execute(category);
    }

    @Override
    public void saveCategories(ArrayList<Category> categoriesToSave) {
        for (Category category : categoriesToSave) {
            try {
                createCategory(category);
            } catch (Exception ex) {
                Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void deleteCategories(ArrayList<Category> categoriesToDelete) {
        for (Category category : categoriesToDelete) {
            try {
                deleteCategory(category);
            } catch (Exception ex) {
                Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
