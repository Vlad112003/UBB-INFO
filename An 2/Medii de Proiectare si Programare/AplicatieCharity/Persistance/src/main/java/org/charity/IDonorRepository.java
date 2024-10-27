package org.charity;

import org.charity.Donor;

public interface IDonorRepository extends IRepository<Integer, Donor>{
    Integer saveWitoutId(Donor entity);

    Iterable<Donor> findAllWithFilter(String filter);
}
