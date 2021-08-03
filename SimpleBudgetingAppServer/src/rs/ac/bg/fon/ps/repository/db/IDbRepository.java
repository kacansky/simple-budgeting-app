/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.repository.db;

import rs.ac.bg.fon.ps.repository.IRepository;

/**
 *
 * @author Dunja
 */
public interface IDbRepository<T, K> extends IRepository<T, K> {

    default public void connect() throws Exception {
        DbConnectionFactory.getInstance().getConnection();
    }

    default public void disconnect() throws Exception {
        DbConnectionFactory.getInstance().getConnection().close();
    }

    default public void commit() throws Exception {
        DbConnectionFactory.getInstance().getConnection().commit();
    }

    default public void rollback() throws Exception {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }
}
