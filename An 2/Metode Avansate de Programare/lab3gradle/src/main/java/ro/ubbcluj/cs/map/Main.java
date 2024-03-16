package ro.ubbcluj.cs.map;

import java.sql.Timestamp;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import ro.ubbcluj.cs.map.domain.*;
import ro.ubbcluj.cs.map.repository.*;
import ro.ubbcluj.cs.map.service.*;
import ro.ubbcluj.cs.map.ui.*;
import ro.ubbcluj.cs.map.domain.validators.*;

public class Main {
    public static void main(String[] args) {

//        Tests teste = new Tests();
//        teste.testAll();

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
