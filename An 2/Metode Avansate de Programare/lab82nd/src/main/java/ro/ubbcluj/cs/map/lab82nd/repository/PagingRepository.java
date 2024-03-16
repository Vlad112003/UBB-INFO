package ro.ubbcluj.cs.map.lab82nd.repository;

import ro.ubbcluj.cs.map.lab82nd.domain.Entity;

public interface PagingRepository <ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAll(Pageable pageable);
}
