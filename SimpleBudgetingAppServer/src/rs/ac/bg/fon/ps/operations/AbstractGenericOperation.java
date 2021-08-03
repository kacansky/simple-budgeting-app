/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.operations;

import rs.ac.bg.fon.ps.repository.db.IDbRepository;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBGeneric;


/**
 *
 * @author Dunja
 */
public abstract class AbstractGenericOperation {

    protected final IDbRepository repository;

    public AbstractGenericOperation() {
        this.repository = new RepositoryDBGeneric();
    }

    public void execute(Object object) throws Exception {
        try {
            startTransaction();
            preconditions(object);
            executeOperation(object);
            commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction();
            throw new Exception(e.getMessage());
        } finally {
            disconnect();
        }
    }

    private void startTransaction() throws Exception {
        this.repository.connect();
    }

    protected abstract void preconditions(Object object) throws Exception;

    protected abstract void executeOperation(Object object) throws Exception;

    private void commitTransaction() throws Exception {
        this.repository.commit();
    }

    private void rollbackTransaction() throws Exception {
        this.repository.rollback();
    }

    private void disconnect() throws Exception {
        this.repository.disconnect();
    }

}
