package ro.ubbcluj.cs.map.lab82nd;

import ro.ubbcluj.cs.map.lab82nd.domain.*;
import ro.ubbcluj.cs.map.lab82nd.domain.validators.*;
import ro.ubbcluj.cs.map.lab82nd.repository.FriendshipDatabase;
import ro.ubbcluj.cs.map.lab82nd.repository.Repository;
import ro.ubbcluj.cs.map.lab82nd.repository.UserDatabase;
import ro.ubbcluj.cs.map.lab82nd.service.Service;
import ro.ubbcluj.cs.map.lab82nd.ui.UI;
import ro.ubbcluj.cs.map.lab82nd.repository.*;
import ro.ubbcluj.cs.map.lab82nd.service.*;
import ro.ubbcluj.cs.map.lab82nd.ui.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    public static void main(String[] args) {

//        Tests teste = new Tests();
//        teste.testAll();

//        Repository repoUser = new InMemoryRepository();
//        Repository repoFriendship = new InMemoryRepository();
        Repository<UUID, User> repoUser = new UserDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
        Repository<UUID, FriendShip> repoFriendship = new FriendshipDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
        Repository<UUID, Message> repoMessage = new MessageDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        MessageValidator messageValidator = new MessageValidator();
//        Service service = new Service(repoUser, userValidator, repoFriendship, friendshipValidator, repoMessage, messageValidator);
//        UI ui = new UI(service);
//        ui.run();

//        Message message = new Message(UUID.fromString("a1b2c3d4-e5f6-a7b8-c9d0-e1f2a3b4c5d6"), UUID.fromString("a1b2c3d4-e5f6-a7b8-c9d0-e1f2a3b4c5d6"), "Hello");
//        System.out.println(message);
    }
}

