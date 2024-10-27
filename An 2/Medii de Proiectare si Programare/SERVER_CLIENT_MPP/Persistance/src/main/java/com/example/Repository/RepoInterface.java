package com.example.Repository;

import com.example.Domain.Entity;


public interface RepoInterface<ID,E extends Entity<ID>> {
    Iterable<E> find_all();
    void delete(E entity);
    void save(E entity);
    void update(ID id,E entity);
}
