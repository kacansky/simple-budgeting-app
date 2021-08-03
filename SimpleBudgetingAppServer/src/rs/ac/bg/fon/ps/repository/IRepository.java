/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository;

import java.util.List;

/**
 *
 * @author Dunja
 */
public interface IRepository<T, K> {

    public Long insert(T t) throws Exception;

    public void update(T t) throws Exception;

    public void delete(T t) throws Exception;

    public List<T> getAll(T entity) throws Exception;
    
    public List<T> getAllFiltered(T entity, K key) throws Exception;
    
}
