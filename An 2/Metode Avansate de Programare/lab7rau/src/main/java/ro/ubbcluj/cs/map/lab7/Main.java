package ro.ubbcluj.cs.map.lab7;

import ro.ubbcluj.cs.map.lab7.domain.*;
import ro.ubbcluj.cs.map.lab7.domain.validators.*;
import ro.ubbcluj.cs.map.lab7.repository.FriendshipDatabase;
import ro.ubbcluj.cs.map.lab7.repository.Repository;
import ro.ubbcluj.cs.map.lab7.repository.UserDatabase;
import ro.ubbcluj.cs.map.lab7.service.Service;
import ro.ubbcluj.cs.map.lab7.ui.UI;
import ro.ubbcluj.cs.map.lab7.repository.*;
import ro.ubbcluj.cs.map.lab7.service.*;
import ro.ubbcluj.cs.map.lab7.ui.*;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Tests teste = new Tests();
        teste.testAll();

//        Repository repoUser = new InMemoryRepository();
//        Repository repoFriendship = new InMemoryRepository();
        Repository<UUID, User> repoUser = new UserDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
        Repository<UUID, FriendShip> repoFriendship = new FriendshipDatabase("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "piupiu");
        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        Service service = new Service(repoUser, userValidator, repoFriendship, friendshipValidator);
        UI ui = new UI(service);

//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime time = LocalDateTime.now();
//        String timeString = time.format(formatter);
////        timeString = timeString.replace(" ", "T");
//        System.out.println(timeString);
////
//        LocalDateTime time2 = LocalDateTime.parse(timeString, formatter);
////
//        System.out.println(time2);
////        System.out.println(time2.toString().replace("T", " "));

//        String time = "2023-11-13 16:53";
//        System.out.println(time);
//        //output = 2023-11-13 16:53
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime time2 = LocalDateTime.parse(time, formatter);
//        System.out.println(time2);
//        String time3 = time2.toString();
//        System.out.println(time3);
//        LocalDateTime time4 = LocalDateTime.parse(time3);
//        System.out.println(time4);
//
//        //output = 2023-11-13T16:53
//        System.out.println(time2.toString() == time);
//        //output = false (duh)



        ui.run();






    }
}
