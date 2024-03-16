package ro.ubbcluj.cs.map.lab7.ui;

import ro.ubbcluj.cs.map.lab7.domain.*;
import ro.ubbcluj.cs.map.lab7.service.*;

import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class UI implements UIInterface<UUID>{

    Service service;

    public UI(Service service){
        this.service = service;
    }

    public void run(){
        menu();
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        while(i != 0){
            option(i);
            menu();
            i = scanner.nextInt();
        }
        System.out.println("Exiting...");
    }

    private void menu(){
        System.out.println("Menu");
        System.out.println("1. Add user");
        System.out.println("2. Remove user");
        System.out.println("3. Add friendship");
        System.out.println("4. Remove friendship");
        System.out.println("5. Show all users");
        System.out.println("6. Show all friendships");
        System.out.println("7. Show number of communities");
        System.out.println("8. Show most sociable community");
        System.out.println("9. Add predefined values");
        System.out.println("10. Get all friendships made by a user in a certain month");
        System.out.println(("0. Exit"));
    }

    private void option(int i){
        switch(i){
            case 1:
                //add user
                uiAddUser();
                break;
            case 2:
                //remove user
                uiRemoveUser();
                break;
            case 3:
                //add friendship
                uiAddFriendship();
                break;
            case 4:
                //remove friendship
                uiRemoveFriendship();
                break;
            case 5:
                //show all users
                uiShowAllUsers();
                break;
            case 6:
                //show all friendships
                uiShowAllFriendships();
                break;
            case 7:
                //show number of communities
                uiShowNumberOfCommunities();
                break;
            case 8:
                //show most sociable community
                uiShowMostSociableCommunity();
                break;
            case 9:
                //add predefined values
                uiAddPredefinedValues();
                break;
            case 10:
                //get all friendships made by a user in a certain month
                uiGetFriendshipsByUserInAMonth();
                break;

            default:
                System.out.println("There is no option with this number!");
        }
    }

    private void uiGetFriendshipsByUserInAMonth() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the user's email: ");
        String email = scanner.nextLine();
        System.out.println("Enter the month: ");
        int month = scanner.nextInt();
        try{
            Iterable<FriendShip> friendships = service.getFriendshipsForUserFromMonth(email, month);
            friendships.forEach(System.out::println);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void uiAddUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the user's first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter the user's last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter the user's email: ");
        String email = scanner.nextLine();
        System.out.println("Enter the user's password: ");
        String password = scanner.nextLine();
        User user = new User(firstName, lastName, email, password);
        try{
            service.addUser(user);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void uiRemoveUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the user's email: ");
        String email = scanner.nextLine();
        try{
            service.deleteUser(email);
            System.out.println("User removed successfully!");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void uiAddFriendship(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the first user's email: ");
        String email1 = scanner.nextLine();
        System.out.println("Enter the second user's email: ");
        String email2 = scanner.nextLine();
        try{
            service.createFriendship(email1, email2);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void uiRemoveFriendship(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the first user's email: ");
        String email1 = scanner.nextLine();
        System.out.println("Enter the second user's email: ");
        String email2 = scanner.nextLine();
        try{
            service.deleteFriendship(email1, email2);
            System.out.println("Friendship removed successfully!");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void uiShowAllUsers(){
        for(User user : service.getAllUsers()){
            System.out.println(user);
        }
        if(service.getUserCount() == 1){
            System.out.println(service.getUserCount() + " user exists.");
        }
        else System.out.println(service.getUserCount() + " users exist.");
    }

    void uiShowAllFriendships(){
        for(FriendShip friends : service.getAllFriendships()){
            System.out.println(friends);
        }
        if (service.getFriendshipCount() == 1)
            System.out.println(service.getFriendshipCount() + " friendships exists.");
        else{
            System.out.println(service.getFriendshipCount() + " friendships exist.");
        }
    }

    void uiShowNumberOfCommunities(){
        if (service.numberOfCommunities() == 1){
            System.out.println(service.numberOfCommunities() + " community exists.");
        }
        else System.out.println(service.numberOfCommunities() + " communities exist.");
    }

    void uiShowMostSociableCommunity(){
        Iterable<Iterable<User>> list = service.mostSociableCommunity();
        AtomicInteger count = new AtomicInteger();
        list.forEach(x -> count.getAndIncrement());
        if (count.get() != 0)
            System.out.println("The most sociable community is: " + list);
        else System.out.println("There are no communities!");
    }

    void uiAddPredefinedValues(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the iteration of predefined values you want to add: ");
            System.out.println("1. 2 Communities, 7 Users, 6 Friendships, Max Path: 3");
            System.out.println("2. 2 Communities, 8 Users, 6 Friendships, Max Path: 5");
            System.out.println("3. 1 Community, 4 Users, 3 Friendships, Max Path: 3");
            System.out.println("4. 3 Communities, 4 Users, 1 Friendships, Max Path: 1");
            System.out.println("5. 8 Communities, 8 Users, 0 Friendships, Max Path: 0");

            int i = scanner.nextInt();
            service.add_Predefined_Values(i);
            System.out.println("Predefined values added successfully!");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }



}
