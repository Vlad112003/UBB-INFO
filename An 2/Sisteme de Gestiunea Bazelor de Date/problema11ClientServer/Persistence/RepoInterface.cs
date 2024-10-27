using System;
using System.Collections.Generic;
using Model;

namespace Persistence
{
    public interface RepoInterface<Id, E> where E : Entity<Guid>
    {
        /**
         * @param id - the id of the entity to be found
         * @returns the entity with the given id
         */
        public E FindOne(Id id);
        
        /**
         * @returns all entities
         */
        public IEnumerable<E> FindAll();
        
        /**
         * saves the entity
         * @param e - the entity to be saved
         * @returns null if the entity was saved, the entity otherwise
         */
        public E save(E e);
        
        /**
         * removes the entity with the specified id
         * @param id - the id of the entity to be deleted
         * @returns the deleted entity
         */
        public E delete(Id id);
        
        /**
         * updates the entity with the specified id
         * @param e - the entity to be updated
         * @returns null if the entity was updated, the entity otherwise
         */
        public E update(E e);
        
        /**
         * @returns the size of the repository
         */
        int size();


    }
}