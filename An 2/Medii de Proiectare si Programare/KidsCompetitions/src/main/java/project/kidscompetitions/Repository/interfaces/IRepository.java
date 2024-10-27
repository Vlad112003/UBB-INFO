package project.kidscompetitions.Repository.interfaces;

import project.kidscompetitions.Domain.Entity;

import java.util.Optional;

public interface IRepository<ID, E extends Entity<ID>> {
    Optional<E> findOne(ID id);
    Iterable<E> findAll();
    Optional<E> save(E entity);
    Optional<E> delete(ID id);
    Optional<E> update(E entity);

    int size();
}
