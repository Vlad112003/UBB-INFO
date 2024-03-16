package ro.ubbcluj.cs.map.lab72nd;

import ro.ubbcluj.cs.map.lab72nd.domain.*;
import ro.ubbcluj.cs.map.lab72nd.domain.validators.*;
import ro.ubbcluj.cs.map.lab72nd.repository.*;
import ro.ubbcluj.cs.map.lab72nd.service.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class Tests {

    private static final String STRING128 = "rnpgybinh7t7rx7tNS7S63rTNvZMGxLwrnpgybinh7t7rx7tNS7S63rTNvZMGxLwrnpgybinh7t7rx7tNS7S63rTNvZMGxLwrnpgybinh7t7rx7tNS7S63rTNvZMGxLw";
    private static final String vledEmail = "vled.vled@vled.com";
    private static final UUID uuid = UUID.randomUUID();

    private static final FriendRequest pending = FriendRequest.PENDING;
    private static final FriendRequest accepted = FriendRequest.ACCEPTED;
    private static final FriendRequest rejected = FriendRequest.REJECTED;


    Tests(){};

    public void testAll(){
        testDomain();
        testRepository();
        testService();
    }

    public void testDomain(){
        testEntity();
        testUser();
        testValidatorUser();
        testFriendship();
        testValidatorFriendship();
    }

    public void testEntity(){
        Entity entity = new Entity();
        entity.setId(uuid);
        assert(entity.getId() == uuid);
        int entityHashCode = entity.hashCode();

        Entity entity2 = new Entity();
        assert(!entity.equals(entity2));

        int entity2HashCode = entity2.hashCode();
        assert(entityHashCode != entity2HashCode);

        assert(entity.toString().equals("Entity{id=" + uuid + "}"));
    }
    public void testUser(){
        System.out.println("Testing Domain classes...");
        User user = new User("Vled", "Vled", vledEmail, "vled");
        assert(user.getFirstName().equals("Vled"));
        assert(user.getLastName().equals("Vled"));
        assert(user.getEmail().equals("vled.vled@vled.com"));
        assert(user.getPassword().equals("vled"));

        user.setId(uuid);
        assert(user.getId() == uuid);

        user.setEmail("string");
        assert(user.getEmail().equals("string"));

        user.setFirstName("string");
        assert(user.getFirstName().equals("string"));

        user.setLastName("string");
        assert(user.getLastName().equals("string"));

        user.setPassword("string");
        assert(user.getPassword().equals("string"));

        User user2 = new User("Vled", "Vled", vledEmail, "vled");

        assert(!user.equals(user2));

        int userHashCode = user.hashCode();
        int user2HashCode = user2.hashCode();
        assert(userHashCode != user2HashCode);

        assert (user.toString().equals("User{firstName='string', lastName='string', email='string', password='string'}"));

        user.addFriend(user2);
        assert(user.getNumberOfFriends() == 1);

        user.removeFriend(user2);
        assert(user.getNumberOfFriends() == 0);
    }


    public void testValidatorUser(){
        Validator val = new UserValidator();

        try{
            User user2 = new User(null, "Vled", vledEmail);
            val.validate(user2);
        }
        catch (Exception e){
            assert(e.getMessage().equals("First name must not be null!"));}

        try {
            User user2 = new User(STRING128, "Vled", vledEmail);
            val.validate(user2);
        }
        catch (Exception e){
            assert(e.getMessage().equals("First name is too long!"));}

        try{
            User user2 = new User("", "Vled", vledEmail);
            val.validate(user2);
        }
        catch(Exception e){
            assert(e.getMessage().equals("First name must not be empty!"));}

        try{
            User user2 = new User("2Vled", "Vled", vledEmail);
            val.validate(user2);
        }
        catch (Exception e){
            assert(e.getMessage().equals("First name must start with a letter!"));}

        try{
            User user2 = new User("Vled", null, vledEmail);
            val.validate(user2);
        }
        catch (Exception e) {
            assert (e.getMessage().equals("Last name must not be null!"));}

        try{
            User user2 = new User("Vled", STRING128, vledEmail);
            val.validate(user2);
        }
        catch (Exception e) {
            assert (e.getMessage().equals("Last name is too long!"));}

        try{
            User user2 = new User("Vled", "", vledEmail);
            val.validate(user2);
        }
        catch (Exception e) {
            assert (e.getMessage().equals("Last name must not be empty!"));}

        try{
            User user2 = new User("Vled", "2Vled", vledEmail);
            val.validate(user2);
        }
        catch (Exception e) {
            assert (e.getMessage().equals("Last name must start with a letter!"));}

        try{
            User user2 = new User("Vled", "Vled", null);
            val.validate(user2);
        }
        catch (Exception e) {
            assert (e.getMessage().equals("Email must not be null!"));}

        try{
            User user2 = new User("Vled", "Vled", STRING128);
            val.validate(user2);
        }
        catch (Exception e) {
            assert (e.getMessage().equals("Email is too long!"));}

        try{
            User user2 = new User("Vled", "Vled", "");
            val.validate(user2);
        }
        catch (Exception e) {
            assert (e.getMessage().equals("Email must not be empty!"));}

        try{
            User user2 = new User("Vled", "Vled", "vled.vledvled.com");
            val.validate(user2);
        }
        catch (Exception e) {
            assert (e.getMessage().equals("Email must contain @!"));}

        try {
            User user2 = new User("Vled", "Vled", "vled.vled@@vled.com");
            val.validate(user2);
        }
        catch (Exception e) {
            assert (e.getMessage().equals("Email must contain only one @!"));}
//        System.out.println("Testing ValidatorUser done!");
    }

    public void testFriendship(){
        User user1 = new User("Vled", "Vled", vledEmail, "vled");
        User user2 = new User("Vled2", "Vled2", "2"+vledEmail, "vled2");
        User aux = new User(null, null, null, null);


        FriendShip friends1 = new FriendShip(user1, user2, pending);
        assert(friends1.getRequest().equals(pending));

        friends1.setRequest(accepted);
        assert(friends1.getRequest().equals(accepted));

        LocalDateTime now = LocalDateTime.now();
        FriendShip friends2 = new FriendShip(user1, user2);
        FriendShip auxFriends = new FriendShip(aux, aux, now);

        assert(friends1.getUser1().equals(user1));
        assert(friends1.getUser2().equals(user2));

        auxFriends.setUser1(user1);
        assert(auxFriends.getUser1().equals(user1));

        auxFriends.setUser2(user2);
        assert(auxFriends.getUser2().equals(user2));

        LocalDateTime time = friends1.getFriendsFrom();
        assert(friends1.getFriendsFrom().equals(time));

        friends1.setFriendsFrom(LocalDateTime.now());
        assert(friends1.getFriendsFrom() != time);

        String friends1String = friends1.toString();
        assert(friends1String.equals(friends1.toString()));

        assert(!friends1.equals(friends2));

        int friends1HashCode = friends1.hashCode();
        int auxHashCode = aux.hashCode();
        assert(friends1HashCode != auxHashCode);

    }

    void testValidatorFriendship(){
        User user1 = new User("Vled", "Vled", vledEmail, "vled");
        Validator friendVal = new FriendshipValidator();
        try{
            FriendShip friends1 = new FriendShip(user1, user1);
            friendVal.validate(friends1);
        }
        catch (Exception e){
            assert(e.getMessage().equals("FriendShip's user must have different ids"));}

    }

    public void testRepository(){
        System.out.println("Testing Repository classes...");
        testInMemoryRepository();
    }

    void testInMemoryRepository(){
        Repository repo = new InMemoryRepository();
        assert(repo.size() == 0);

        User user1 = new User("Vled", "Vled", vledEmail, "vled");
        User user2 = new User("Vled2", "Vled2", "2"+vledEmail, "vled2");
        User user3 = new User("Vled3", "Vled3", "3"+vledEmail, "vled3");

        repo.save(user1);
        assert(repo.size() == 1);

        var user1_clone = repo.save(user1);
        assert(user1_clone.equals(Optional.of(user1)));

        user1.setFirstName("Vledd");
        repo.update(user1);
        assert(repo.size() == 1);

        Optional aux = repo.findOne(user1.getId());
        assert(aux.equals(Optional.of(user1)));

        var all = repo.findAll();
        assert(all.iterator().next().equals(user1));

        repo.delete(user1.getId());
        assert(repo.size()==0);

        try{
            repo.delete(user1.getId());
        }
        catch (Exception e){
            assert false;
        }

        try{
            repo.delete(null);
        }
        catch (Exception e){
            assert(e.getMessage().equals("entity must be not null!"));
        }

        try{
            repo.save(null);
        }
        catch (Exception e){
            assert(e.getMessage().equals("entity must be not null!"));}

        try{
            repo.update(null);
        }
        catch (Exception e){
            assert(e.getMessage().equals("entity must be not null!"));}

        try{
            repo.findOne(null);
        }
        catch (Exception e){
            assert(e.getMessage().equals("id must be not null!"));}



    }
    public void testService(){
        System.out.println("Testing Service classes...");
        Repository repoUser = new InMemoryRepository();
        Repository repoFriends = new InMemoryRepository();
        Validator userValidator = new UserValidator();
        Validator friendshipValidator = new FriendshipValidator();
        Service serv = new Service(repoUser, userValidator, repoFriends, friendshipValidator);

        User user1 = new User("Vled", "Vled", vledEmail, "vled");

        serv.addUser(user1);

        User userNullEmail = new User("Vledi", "vledi", null, "vledi");

        try{
            serv.addUser(user1);
        }
        catch (Exception e){
            assert(e.getMessage().equals("Email already exists!"));
        }
        try{
            serv.addUser(userNullEmail);
        }
        catch (Exception e){
            assert(e.getMessage().equals("Email must be not null!"));
        }
        try{
            serv.addUser(null);
        }
        catch (Exception e){
            assert(e.getMessage().equals("User must be not null!"));
        }

        serv.deleteUser(vledEmail);
        assert(serv.getUserByEmail(vledEmail) == null);

        try{
            serv.deleteUser(null);
        }
        catch (Exception e){
            assert(e.getMessage().equals("Email must be not null!"));
        }

        try{
            serv.deleteUser("string");
        }
        catch (Exception e){
            assert(e.getMessage().equals("Email doesn't exist!"));
        }

        serv.addUser(user1);

        User user2 = new User("Vled2", "Vled2", "2"+vledEmail, "vled2");
        serv.addUser(user2);

        serv.createFriendship(vledEmail, "2"+vledEmail);

        try{
            serv.createFriendship(vledEmail, "2"+vledEmail);
        }
        catch (Exception e){
            assert(e.getMessage().equals("Friendship already exists!"));
        }

        try{
            serv.createFriendship(vledEmail, "string");
        }
        catch (Exception e){
            assert(e.getMessage().equals("Users must be not null and different!"));
        }

        try{
            serv.createFriendship(vledEmail, vledEmail);
        }
        catch (Exception e){
            assert(e.getMessage().equals("Users must be not null and different!"));
        }

        try{
            serv.createFriendship(null, "2"+vledEmail);
        }
        catch (Exception e){
            assert(e.getMessage().equals("Emails must be not null!"));
        }

        try{
            serv.createFriendship(vledEmail, "2"+vledEmail);
        }
        catch (Exception e){
            assert(e.getMessage().equals("Friendship already exists!"));
        }

        assert(serv.getFriendshipCount() == 1);
        serv.deleteFriendship(vledEmail, "2"+vledEmail);
        assert(serv.getFriendshipCount() == 0);

        try{
            serv.deleteFriendship(vledEmail, "2"+vledEmail);
        }
        catch (Exception e){
            assert(e.getMessage().equals("These users are not friends!"));
        }

        try{
            serv.deleteFriendship(vledEmail, vledEmail);
        }
        catch (Exception e){
            assert(e.getMessage().equals("Users must be not null and different!"));
        }

        try{
            serv.deleteFriendship(null, "2"+vledEmail);
        }
        catch (Exception e){
            assert(e.getMessage().equals("Emails must not be null!"));
        }


        User user3 = new User("Vled3", "Vled3", "3"+vledEmail, "vled3");
        serv.addUser(user3);

        User user4 = new User("Vled4", "Vled4", "4"+vledEmail, "vled4");
        serv.addUser(user4);


        serv.createFriendship(vledEmail, "3"+vledEmail);
        assert(serv.numberOfCommunities() == 3);

        serv.createFriendship(vledEmail, "4"+vledEmail);
        assert(serv.numberOfCommunities() == 2);

        serv.createFriendship("3"+vledEmail, "4"+vledEmail);
        assert(serv.numberOfCommunities() == 2);

        serv.createFriendship(vledEmail, "2"+vledEmail);
        assert(serv.numberOfCommunities() == 1);

        var coms = serv.getAllCommunities();
        assert(coms.size() == 1);


        serv.deleteFriendship(vledEmail, "3"+vledEmail);
        serv.deleteFriendship(vledEmail, "4"+vledEmail);
        serv.deleteFriendship(vledEmail, "2"+vledEmail);
        serv.deleteFriendship("3"+vledEmail, "4"+vledEmail);

        assert(serv.getFriendshipCount() == 0);

        assert(serv.getUserCount() == 4);
        serv.deleteUser(vledEmail);
        serv.deleteUser("2"+vledEmail);
        serv.deleteUser("3"+vledEmail);
        serv.deleteUser("4"+vledEmail);
        assert(serv.getUserCount() == 0);

        //doua comunitati, drum maxim = 6
        serv.add_Predefined_Values(5);
        var list = serv.mostSociableCommunity();
//            for( var x : list){
//                System.out.println(x);
//            }
        //assert(list.toString().equals("[[User{firstName='Vled1', lastName='Vled1', email='vled.vlad1@vled.com', password='vled1'}, User{firstName='Vled2', lastName='Vled2', email='vled.vlad2@vled.com', password='vled2'}]]") || list.toString().equals("\"[[User{firstName='Vled2', lastName='Vled2', email='vled.vlad2@vled.com', password='vled2'}, User{firstName='Vled', lastName='Vled', email='vled.vlad@vled.com', password='vled'}]]\""));

        assert(serv.getUserCount() == 8);
        assert(serv.getFriendshipCount() == 0);

        serv.createFriendRequest("vled.vlad1@vled.com", "vled.vlad2@vled.com");
        serv.acceptFriendship("vled.vlad1@vled.com", "vled.vlad2@vled.com");
        assert(serv.getFriendshipCount() == 1);

        serv.createFriendRequest("vled.vlad3@vled.com", "vled.vlad4@vled.com");
        serv.declineFriendRequest("vled.vlad3@vled.com", "vled.vlad4@vled.com");
        //System.out.println(serv.getFriendshipCount());

        var users = serv.getAllUsers();

        var friends = serv.getAllFriendships();



    }
}
