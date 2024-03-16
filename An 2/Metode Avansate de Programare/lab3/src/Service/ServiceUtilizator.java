package Service;

import Domain.Prietenie;
import Domain.Utilizator;
import Domain.validators.ValidationException;
import Domain.validators.Validator;
import Repository.InMemoryRepository;

public class ServiceUtilizator {

    private InMemoryRepository<Long, Utilizator> repoUtilizator;

    private InMemoryRepository<Long, Prietenie> repoPrietenie;

    public ServiceUtilizator(InMemoryRepository<Long, Utilizator> repoUtilizator, InMemoryRepository<Long, Prietenie> repoPrietenie) {
        this.repoUtilizator = repoUtilizator;
        this.repoPrietenie = repoPrietenie;
    }

    public void addUtilizator(Utilizator user) {
        try {
            repoUtilizator.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUtilizator(Long id) {
        try {
            repoUtilizator.delete(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public Iterable getRepo(){
        return this.repoUtilizator.findAll();
    }

    public Utilizator getUtilizator(Utilizator user)

    public int size(){
        return this.repoUtilizator.size();
    }
}
