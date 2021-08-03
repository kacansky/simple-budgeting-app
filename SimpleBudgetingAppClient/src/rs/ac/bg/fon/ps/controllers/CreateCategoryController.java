/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.communication.CommunicationController;
import rs.ac.bg.fon.ps.components.table.CategoriesTableModel;
import rs.ac.bg.fon.ps.domain.Category;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.forms.FrmCreateCategory;
import rs.ac.bg.fon.ps.help.HelpData;

/**
 *
 * @author Dunja
 */
public class CreateCategoryController {
    private FrmCreateCategory frmCreateCategory;
    ArrayList<Category> categoriesForView;
    ArrayList<Category> addedCategories;
    ArrayList<Category> deletedCategories;
    User user;
    WalletController parent;

    public CreateCategoryController(FrmCreateCategory frmCreateCategory, WalletController parent) {
        this.frmCreateCategory = frmCreateCategory;
        this.user = HelpData.loggedUser;
        this.parent = parent;
        this.addedCategories = new ArrayList<>();
        this.deletedCategories = new ArrayList<>();
        addActionListener();
    }

    void openForm() {
        frmCreateCategory.setLocationRelativeTo(null);
        frmCreateCategory.setTitle("Categories view");
        frmCreateCategory.setResizable(false);
        frmCreateCategory.setVisible(true);     
        try {
            this.categoriesForView = Communication.getInstance().getCategories(user.getUserID());
            CategoriesTableModel ctm = new CategoriesTableModel(categoriesForView);
            frmCreateCategory.getTblCategories().setModel(ctm);
        } catch (Exception ex) {
            Logger.getLogger(CreateCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addActionListener() {
        frmCreateCategory.closeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmCreateCategory.dispose();
            }
        });
        frmCreateCategory.saveChangesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!deletedCategories.isEmpty()) {
                    try {
                        CommunicationController.getInstance().deleteCategories(deletedCategories);
                    } catch (Exception ex) {
                        Logger.getLogger(CreateCategoryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(!addedCategories.isEmpty()) {
                    try {
                        CommunicationController.getInstance().addCategories(addedCategories);
                        addedCategories = new ArrayList<>();
                    } catch (Exception ex) {
                        Logger.getLogger(CreateCategoryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                parent.setCategories(categoriesForView);
                frmCreateCategory.dispose();
                
            }
        });
        frmCreateCategory.deleteCategoryActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(frmCreateCategory.getTblCategories().getSelectedRow()==-1) {
                    JOptionPane.showMessageDialog(frmCreateCategory, "Please select a category to delete.");
                    return;
                }
                deleteCategory();
            }

            private void deleteCategory() {
                Category categoryToDelete = getCategory(frmCreateCategory.getTblCategories().getSelectedRow());
                System.out.println(categoryToDelete.getCategoryName());
                if(categoryToDelete.getUser().getUserID()==44) {
                    JOptionPane.showMessageDialog(frmCreateCategory, "Default categories cannot be deleted!");
                    return;
                }
                deletedCategories.add(categoryToDelete);
                categoriesForView.remove(categoryToDelete);
                CategoriesTableModel ctm = new CategoriesTableModel(categoriesForView);
                frmCreateCategory.getTblCategories().setModel(ctm);
            }

            private Category getCategory(int selectedRow) {
                Category categoryToDelete = new Category();
                categoryToDelete.setCategoryID((Long) frmCreateCategory.getTblCategories().getModel().getValueAt(selectedRow, 1));
                categoryToDelete.setCategoryName((String) frmCreateCategory.getTblCategories().getModel().getValueAt(selectedRow, 0));
                categoryToDelete.setUser((User) frmCreateCategory.getTblCategories().getModel().getValueAt(selectedRow, 2));
                return categoryToDelete;
            }
        });
        frmCreateCategory.addCategoryActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 try {
                    validateForm();
                } catch (Exception ex) {
                    Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
                }
                addCategory();
                resetForm();
            }

            private void resetForm() {
                frmCreateCategory.getTxtCategoryName().setText("");
            }

            private void validateForm() throws Exception {
                String name = frmCreateCategory.getTxtCategoryName().getText();
                System.out.println(name);
                if(name.isEmpty()) {
                    JOptionPane.showMessageDialog(frmCreateCategory, "Name field cannot be empty!");
                    throw new Exception ("Name field is empty");
                    }

            }

            private void addCategory() {
                String name = frmCreateCategory.getTxtCategoryName().getText();
                Category addedCategory = new Category(name,user);
                addedCategories.add(addedCategory);
                categoriesForView.add(addedCategory);
                CategoriesTableModel ctm = new CategoriesTableModel(categoriesForView);
                frmCreateCategory.getTblCategories().setModel(ctm);
            }
        });
    }
    
    
}
