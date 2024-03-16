package ro.ubbcluj.cs.map.lab72nd;

import ro.ubbcluj.cs.map.lab72nd.domain.*;
import ro.ubbcluj.cs.map.lab72nd.domain.validators.*;
import ro.ubbcluj.cs.map.lab72nd.repository.FriendshipDatabase;
import ro.ubbcluj.cs.map.lab72nd.repository.Repository;
import ro.ubbcluj.cs.map.lab72nd.repository.UserDatabase;
import ro.ubbcluj.cs.map.lab72nd.service.Service;
import ro.ubbcluj.cs.map.lab72nd.ui.UI;
import ro.ubbcluj.cs.map.lab72nd.repository.*;
import ro.ubbcluj.cs.map.lab72nd.service.*;
import ro.ubbcluj.cs.map.lab72nd.ui.*;

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
        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        Service service = new Service(repoUser, userValidator, repoFriendship, friendshipValidator);
        UI ui = new UI(service);

//
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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

//        System.out.println(StreamSupport.stream(service.getAllFriendships().spliterator(),
//                false).collect(Collectors.toList()));
//
//        ArrayList<String> list = new ArrayList<>();
//        Iterator<FriendShip> iterator = service.getAllFriendships().iterator();
//        while(iterator.hasNext()) {
//            FriendShip fr = iterator.next();
//            System.out.println(fr.getUser1().getEmail());
//            System.out.println(fr.getUser2().getEmail());
//            System.out.println(fr.getFriendsFrom());
//            ArrayList<String> list2 = new ArrayList<>();
//            list2.add(fr.getUser1().getEmail());
//            list2.add(fr.getUser2().getEmail());
//            list2.add(fr.getFriendsFrom().toString());
//            list.add(list2.toString());
//            StringFriendship stringFriendship = new StringFriendship(fr.getUser1().getEmail(), fr.getUser2().getEmail(), fr.getFriendsFrom().toString());
//            System.out.println(stringFriendship);
//        }
////        System.out.println(list);
//        System.out.println(StreamSupport.stream(list.spliterator(), false)
//                .collect(Collectors.toList()));



    }
}
