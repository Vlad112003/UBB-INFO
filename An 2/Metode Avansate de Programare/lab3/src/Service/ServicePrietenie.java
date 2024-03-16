package Service;

import Domain.Entity;
import Domain.Prietenie;
import Domain.Tuple;
import Domain.Utilizator;
import Repository.InMemoryRepository;
import jdk.jshell.execution.Util;

public class ServicePrietenie {

    private InMemoryRepository<Long, Utilizator> repoUtilizator;

    private InMemoryRepository<Long, Prietenie> repoPrietenii;
    public ServicePrietenie(InMemoryRepository<Long, Utilizator> repoUtilizator, InMemoryRepository<Long, Prietenie> repoPrietenii) {
        this.repoUtilizator = repoUtilizator;
        this.repoPrietenii = repoPrietenii;

    }

    public void addPrietenie(Utilizator user1, Utilizator user2) {
        try {
            repoPrietenii.save(user1);
            repoPrietenii.save(user2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
