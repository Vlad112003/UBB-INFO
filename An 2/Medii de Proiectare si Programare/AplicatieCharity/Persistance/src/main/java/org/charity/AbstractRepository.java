package org.charity;


import org.charity.Entity;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepository<ID, T extends Entity<ID>> implements IRepository<ID, T> {
    protected Map<ID, T> entities;

    public AbstractRepository(){
        entities=new HashMap<>();
    }
    @Override
    public int size() {
        return entities.size();
    }

    @Override
    public void update(ID id, T entity) {
        if (!(entities.get(id)==null)) {
            if (!id.equals(entity.getId()))
                if (entities.get(entity.getId())!=null)
                    throw new RepositoryException("Id "+entity.getId()+" already exists!!");
            entities.put(entity.getId(), entity);
            System.out.println("Entitate modificata " + entity);
        }else
            throw new RepositoryException("Id "+id +" does not  exists");
    }

    @Override
    public void save(T entity) {
        // setEntityId(entity);
        ID id=entity.getId();
        if (entities.get(id)==null) {
            entities.put(id, entity);
        }else
            throw new RepositoryException("Id already exists"+id);

    }

    @Override
    public void delete(ID id) {
        entities.remove(id);
        System.out.println("Entity deleted "+id);
    }

    @Override
    public T findOne(ID id) {
        T res=entities.get(id);
        if (res!=null)
            return res;
        throw new RepositoryException("Id not found "+id);
    }

    @Override
    public Iterable<T> findAll() {
        return entities.values();
    }

    //protected abstract void setEntityId(T entity);
}

