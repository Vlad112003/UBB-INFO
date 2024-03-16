import domain.User;
import domain.FriendShip;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import repository.FriendshipDatabase;
import repository.InMemoryRepository;
import repository.Repository;
import repository.UserDatabase;
import service.Service;
import ui.UI;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Tests teste = new Tests();
        teste.testAll();

//        Repository repoUser = new InMemoryRepository();
//        Repository repoFriendship = new InMemoryRepository();
        Repository<UUID, User> repoUser = new UserDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
        Repository<UUID, FriendShip> repoFriendship = new FriendshipDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu", repoUser);
        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        Service service = new Service(repoUser, userValidator, repoFriendship, friendshipValidator);
        UI ui = new UI(service);

        ui.run();



//        User u1 = new User("v", "v1", "1@.com");
//        User u2 = new User ("v", "v2", "2@.com");
//        User u3 = new User ("v", "v3", "3@.com");
//
//
//        service.addUser(u1);
//        service.addUser(u2);
//        service.addUser(u3);
//
//        service.createFriendship(u1.getEmail(), u2.getEmail());
//        service.createFriendship(u1.getEmail(), u3.getEmail());
//        service.createFriendship(u2.getEmail(), u3.getEmail());
//
//        for(FriendShip u : service.getAllFriendships()){
//            System.out.println(u);
//        }
//        service.deleteUser(u1.getEmail());
//        System.out.println();
//        for(FriendShip u : service.getAllFriendships()){
//            System.out.println(u);
//        }






    }
}
