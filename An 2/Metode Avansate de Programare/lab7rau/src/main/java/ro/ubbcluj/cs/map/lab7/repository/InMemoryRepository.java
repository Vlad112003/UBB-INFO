package ro.ubbcluj.cs.map.lab7.repository;

import ro.ubbcluj.cs.map.lab7.domain.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E>{
    protected Map<ID, E> entities;


    public InMemoryRepository(){
        entities = new HashMap<ID, E>();
    }

    @Override
    public Optional<E> findOne(ID id){
        if (id == null)
            throw new IllegalArgumentException("id must be not null!");
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        if(entities.get(entity.getId()) != null) {
            return Optional.of(entity);
        }
        else
            entities.put(entity.getId(),entity);
        return null;
    }

    @Override
    public Optional<E> delete(ID id) {
        if(id == null)
            throw new IllegalArgumentException("entity must be not null!");

        E entity = entities.get(id);
        if(entity == null)
            return null;

        return Optional.ofNullable(entities.remove(entity.getId()));
    }

    @Override
    public Optional<E> update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");

        entities.put(entity.getId(),entity);

        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(), entity);
            return null;
        }
        return Optional.of(entity);
    }

    @Override
    public int size(){
        return entities.size();
    }
}