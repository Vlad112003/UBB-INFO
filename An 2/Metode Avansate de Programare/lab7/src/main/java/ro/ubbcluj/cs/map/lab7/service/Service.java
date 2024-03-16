package ro.ubbcluj.cs.map.lab7.service;

import ro.ubbcluj.cs.map.lab7.domain.*;
import ro.ubbcluj.cs.map.lab7.domain.validators.*;
import ro.ubbcluj.cs.map.lab7.repository.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static ro.ubbcluj.cs.map.lab7.domain.FriendRequest.*;


public class Service implements ServiceInterface {

    private final Repository<UUID, User> userRepo;
    private final Validator<User> userValidator;
    private final Repository<UUID, FriendShip> friendshipRepo;
    private final Validator<FriendShip> friendshipValidator;

    /**
     * Constructor
     *
     * @param userRepo            - the repository for the users
     * @param userValidator       - the validator for the users
     * @param friendshipRepo      - the repository for the friendships
     * @param friendshipValidator - the validator for the friendships
     */
    public Service(Repository<UUID, User> userRepo, Validator<User> userValidator, Repository<UUID, FriendShip> friendshipRepo, Validator<FriendShip> friendshipValidator) {
        this.userRepo = userRepo;
        this.userValidator = userValidator;
        this.friendshipRepo = friendshipRepo;
        this.friendshipValidator = friendshipValidator;
    }

    /**
     * Adds an user
     *
     * @param user - the user we want to add
     * @return true if the user was added, false otherwise
     * <p>
     * We use this method to add an user to the repository.
     * We first check if the user is not null and if the email is not null and if the user doesn't already exist.
     * Then we check if the user is valid and if it is, we add it to the repository.
     * If the user already exists, we throw an exception.
     */
    @Override
    public boolean addUser(User user) {
        Optional u = null;
        if (user == null) {
            throw new IllegalArgumentException("User must be not null!");
        }
        if (user.getEmail() == null) {
            throw new IllegalArgumentException("Email must be not null!");
        } else if (getUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists!");
        }
        try {
            this.userValidator.validate(user);
            u = userRepo.save(user);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }

        if (u != null) {
            throw new IllegalArgumentException("An user with this ID already exists!");
        }
        return true;
    }


    /**
     * Deletes an user
     *
     * @param email - the email of the user we want to delete
     * @return Entity<UUID> - the deleted user
     * <p>
     * We use this method to delete an user from the repository.
     * We first check if the email is not null and if the user exists.
     * Then we delete the user.
     * If the user doesn't exist, we throw an exception.
     */
    @Override
    public Optional deleteUser(String email) {
        if (email == null)
            throw new IllegalArgumentException("Email must be not null!");
        User u = getUserByEmail(email);
        if (u == null)
            throw new IllegalArgumentException("Email doesn't exist!");

        try {
            deleteFriendship(email);
        } catch (Exception e) {
        }
        return Optional.of(userRepo.delete(u.getId()));
    }

    /**
     * Creates a friendship between two users
     *
     * @param email1 and
     * @param email2 - the emails of the user we have to create a friendship between
     * @return true if the friendship was created, false otherwise
     */
    @Override
    public boolean createFriendship(String email1, String email2) {
        Optional f = null;

        if (email1 == null || email2 == null) {
            throw new IllegalArgumentException("Emails must be not null!");
        }
        User u1 = getUserByEmail(email1);
        User u2 = getUserByEmail(email2);
        if (u1 == null || u2 == null || u1.equals(u2)) {
            throw new IllegalArgumentException("Users must be not null and different!");
        }
        var u1Friends = u1.getFriends();
        var u2Friends = u2.getFriends();
        if (u1Friends.contains(u2) || u2Friends.contains(u1)) {
            throw new IllegalArgumentException("Friendship already exists!");
        }
        var frenship = new FriendShip(u1, u2, ACCEPTED);
        try {
            this.friendshipValidator.validate(frenship);
            f = friendshipRepo.save(frenship);
        } catch (Exception e) {
            return false;
        }
        if (f != null) {
            throw new IllegalArgumentException("These users are already friends!");
        }
        u1.addFriend(u2);
        u2.addFriend(u1);
        return true;


    }

    /**
     * Deletes a friendship
     *
     * @param email1 - the email of the first user
     * @param email2 - the email of the second user
     * @return Entity<UUID> - the deleted friendship
     * <p>
     * We use this method to delete a friendship between two users.
     * We first check if the emails are not null and if the users are not null and different.
     * Then we check if the friendship exists and if it does, we delete it.
     * If the friendship doesn't exist, we throw an exception.
     */
    @Override
    public Optional deleteFriendship(String email1, String email2) {
        Optional f = null;
        User u1, u2;

        if (email1 == null || email2 == null)
            throw new IllegalArgumentException("Emails must not be null!");
        u1 = getUserByEmail(email1);
        u2 = getUserByEmail(email2);

        if (u1 == null || u2 == null || u1.equals(u2))
            throw new ValidationException("Users must be not null and different!");

        Iterable<FriendShip> l = friendshipRepo.findAll();
//        for(FriendShip el : l)
//            if(
//                    (el.getUser1().getId().equals(u1.getId()) && el.getUser2().getId().equals(u2.getId()))
//                            || (el.getUser1().getId().equals(u2.getId()) && el.getUser2().getId().equals(u1.getId()))
//            )
//            {
//                f = friendshipRepo.delete(el.getId());
//                break;
//            }
        AtomicReference<FriendShip> found = new AtomicReference<>();
        l.forEach(el -> {
            if (
                    (el.getUser1().getId().equals(u1.getId()) && el.getUser2().getId().equals(u2.getId()))
                            || (el.getUser1().getId().equals(u2.getId()) && el.getUser2().getId().equals(u1.getId()))
            ) {
                found.set(el);
            }
        });
        if (found.get() == null) {
            throw new IllegalArgumentException("These users are not friends!");
        }
        f = friendshipRepo.delete(found.get().getId());


//        if(f == null) {
//            throw new IllegalArgumentException("These users are not friends!");
//        }

        u1.removeFriend(u2);
        u2.removeFriend(u1);
        return f;
    }

    @Override
    public Optional deleteFriendship(String email) {
        Optional f = null;
        User u1;

        if (email == null)
            throw new IllegalArgumentException("Email must not be null!");
        u1 = getUserByEmail(email);

        //remove all friends for said user
        for (User u : u1.getFriends()) {
            u.removeFriend(u1);
        }

        //remove all friendships for said user from repo
        Iterable<FriendShip> l = friendshipRepo.findAll();
        l.forEach(el -> {
            if (el.getUser1().getEmail().equals(u1.getEmail()) || el.getUser2().getEmail().equals(u1.getEmail()))
                friendshipRepo.delete(el.getId());
        });


//        Iterable<FriendShip> l2 = friendshipRepo.findAll();
//        for(FriendShip el : l2)
//            if((el.getUser1().getEmail().equals(u1.getEmail()) || el.getUser2().getEmail().equals(u1.getEmail())))
//                f = friendshipRepo.delete(el.getId());


        if (f == null) {
            throw new IllegalArgumentException("These users are not friends!");
        }
        return f;
    }


    /**
     * Returns all the users
     *
     * @return Iterable<User> - all the users
     */
    @Override
    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Returns all the friendships
     *
     * @return Iterable<FriendShip> - all the friendships
     */
    @Override
    public Iterable<FriendShip> getAllFriendships() {
        return friendshipRepo.findAll();
    }

    /**
     * Adds some predefined values to the repository
     *
     * @param i - the iteration number
     *          i = 1 - 2 communities, with the maximum path being 2
     *          i = 2 - 2 communities, with the maximum path being 6
     *          i = 3 - 1 community, with the maximum path being 3
     *          i = 4 - 4 communities, with the maximum path being 0
     *          We use this method to add some predefined values to the repository
     *          for testing purposes.
     */
    @Override
    public void add_Predefined_Values(int i) {
        User u1 = new User("Vled1", "Vled1", "vled.vlad1@vled.com", "vled1");
        User u2 = new User("Vled2", "Vled2", "vled.vlad2@vled.com", "vled2");
        User u3 = new User("Vled3", "Vled3", "vled.vlad3@vled.com", "vled3");
        User u4 = new User("Vled4", "Vled4", "vled.vlad4@vled.com", "vled4");
        User u5 = new User("Vled5", "Vled5", "vled.vlad5@vled.com", "vled5");
        User u6 = new User("Vled6", "Vled6", "vled.vlad6@vled.com", "vled6");
        User u7 = new User("Vled7", "Vled7", "vled.vlad7@vled.com", "vled7");
        User u8 = new User("Vled8", "Vled8", "vled.vlad8@vled.com", "vled8");
        switch (i) {
            case 1:
                // Doua comunitati, cu drumul maxim fiind 3 (4 noduri)
            {
                this.addUser(u1);
                this.addUser(u2);
                this.addUser(u3);
                this.addUser(u4);
                this.addUser(u5);
                this.addUser(u6);
                this.addUser(u7);

                this.createFriendship(u1.getEmail(), u2.getEmail());
                this.createFriendship(u1.getEmail(), u7.getEmail());

                this.createFriendship(u6.getEmail(), u5.getEmail());
                this.createFriendship(u5.getEmail(), u4.getEmail());
                this.createFriendship(u4.getEmail(), u3.getEmail());
            }
            break;

            case 2:
                // Doua comunitati, cu drumul maxim fiind 5 (6 noduri)
            {
                ;

                this.addUser(u1);
                this.addUser(u2);
                this.addUser(u3);
                this.addUser(u4);
                this.addUser(u5);
                this.addUser(u6);
                this.addUser(u7);
                this.addUser(u8);

                this.createFriendship(u1.getEmail(), u2.getEmail());

                this.createFriendship(u3.getEmail(), u4.getEmail());
                this.createFriendship(u4.getEmail(), u5.getEmail());
                this.createFriendship(u5.getEmail(), u6.getEmail());
                this.createFriendship(u6.getEmail(), u7.getEmail());
                this.createFriendship(u7.getEmail(), u8.getEmail());
            }
            break;

            case 3: {
                // O comunitate, drum maxim de 3 (4 noduri)
                this.addUser(u1);
                this.addUser(u2);
                this.addUser(u3);
                this.addUser(u4);

                this.createFriendship(u1.getEmail(), u2.getEmail());
                this.createFriendship(u2.getEmail(), u3.getEmail());
                this.createFriendship(u3.getEmail(), u4.getEmail());
            }
            break;

            case 4: {
                // 3 comunitati , drum max de 1 (2 noduri)

                this.addUser(u1);
                this.addUser(u2);
                this.addUser(u3);
                this.addUser(u4);

                this.createFriendship(u1.getEmail(), u2.getEmail());
            }
            break;

            case 5: { //8 comunitati, drum maxim = 0 (1 nod)
                this.addUser(u1);
                this.addUser(u2);
                this.addUser(u3);
                this.addUser(u4);
                this.addUser(u5);
                this.addUser(u6);
                this.addUser(u7);
                this.addUser(u8);
            }
            break;

            default:
                System.out.println("There is no iteration with this number!");
        }

    }

    /**
     * Returns the number of communities
     *
     * @return int - the number of communities
     * <p>
     * We use a DFS for each node to count the number of conex elements.
     */
    @Override
    public int numberOfCommunities() {
        Iterable<User> it = userRepo.findAll();
        Set<User> set = new HashSet<>();
//        int count = 0;
        AtomicInteger count = new AtomicInteger(0);

        it.forEach(u -> {
            if (!set.contains(u)) {
                count.getAndIncrement();
                DFS(u, set);
            }
        });
        return count.get();

//        for(User u: it){
//            if(!set.contains(u)){
//                ++count;
//                DFS(u, set);
//            }
//        }
//        return count;
    }

    /**
     * a simple DFS algorithm
     *
     * @param u   - the current user
     * @param set - the set of visited users
     * @return List<User> - the list of users in the current community
     */
    private List<User> DFS(User u, Set<User> set) {
        List<User> list = new ArrayList<>();
        list.add(u);
        set.add(u);

        List<User> lista_prieteni = u.getFriends();

        lista_prieteni.forEach(f -> {
            if (!set.contains(f)) {
                List<User> l = DFS(f, set);
                l.forEach(x -> {
                    list.add(x);
                });
            }
        });

//        for(User f : u.getFriends()){
//            if(!set.contains(f)){
//                List<User> l = DFS(f, set);
//                for(User x : l){
//                    list.add(x);
//                }
//            }
//        }
        return list;
    }

    /**
     * Returns the most sociable community
     *
     * @return list - the list of users in the most sociable community
     * <p>
     * <p>
     * We use a BFS to find the longest path in a community.
     */
    @Override
    public Iterable<Iterable<User>> mostSociableCommunity() {
        List<Iterable<User>> list = new ArrayList<>();
        Iterable<User> it = userRepo.findAll();
        Set<User> set = new HashSet<>();

        final int[] max = {-1};

        it.forEach(u -> {
            if (!set.contains(u)) {
                List<User> aux = DFS(u, set);
                int l = longestPath(aux);
                if (l > max[0]) {
                    list.clear();
                    list.add(aux);
                    max[0] = l;
                } else if (l == max[0])
                    list.add(aux);
            }
        });
//        for(User u : it)
//            if(!set.contains(u))
//            {
//                List<User> aux = DFS(u, set);
//                int l = longestPath(aux);
//                if(l > max) {
//                    list = new ArrayList<>();
//                    list.add(aux);
//                    max = l;
//                }
//                else if(l == max)
//                    list.add(aux);
//            }

        return list;
    }

    /**
     * Returns the longest path in a community
     *
     * @param nodes - the list of users in the community
     * @return int - the longest path in the community
     * <p>
     * We use a BFS to find the longest path in a community.
     */
    private int longestPath(List<User> nodes) {
//        int max = 0;
//        for(User u : nodes) {
//            int l = longestPathFromSource(u);
//            if(max < l)
//                max = l;
//        }
//        return max;
        final int[] max = {0};
        nodes.forEach(u -> {
            int l = longestPathFromSource(u);
            if (max[0] < l)
                max[0] = l;
        });
        return max[0];
    }

    /**
     * Returns the longest path from a source
     *
     * @param source - the source user
     * @return int - the longest path from the source
     * <p>
     * We use a BFS to find the longest path from a source.
     */
    private int longestPathFromSource(User source) {
        Set<User> set = new HashSet<>();
        return BFS(source, set);
    }

    /**
     * a simple BFS algorithm
     *
     * @param source - the current user
     * @param set    - the set of visited users
     * @return int - the longest path from the current user
     */
    private int BFS(User source, Set<User> set) {
//        int max = -1;
//        for(User f : source.getFriends())
//            if(!set.contains(f)) {
//                set.add(f);
//                int l = BFS(f, set);
//                if(l > max)
//                    max = l;
//                set.remove(f);
//            }
//
//        return max + 1;
        final int[] max = {-1};
        List<User> c = source.getFriends();
        c.forEach(f -> {
            if (!set.contains(f)) {
                set.add(f);
                int l = BFS(f, set);
                if (l > max[0])
                    max[0] = l;
                set.remove(f);
            }
        });
        return max[0] + 1;
    }

    /**
     * Returns all the communities
     *
     * @return List<List < User>> - all the communities
     * <p>
     * We use a DFS for each node to find all the communities.
     */
    @Override
    public List<List<User>> getAllCommunities() {
//        Iterable<User> it = userRepo.findAll();
//        Set<User> set = new HashSet<>();
//
//        List<List<User>> l = new ArrayList<>();
//
//        for(User u : it){
//            if(!set.contains(u)){
//                l.add(DFS(u, set));
//            }
//        }
//        return l;
        Iterable<User> it = userRepo.findAll();
        Set<User> set = new HashSet<>();

        List<List<User>> l = new ArrayList<>();

        it.forEach(u -> {
            if (!set.contains(u)) {
                l.add(DFS(u, set));
            }
        });
        return l;
    }

    /**
     * Returns the user with the given email
     *
     * @param email - the email of the user we want to find
     * @return User - the user with the given email
     * <p>
     * We use this method to find an user by its email.
     * We first check if the email is not null.
     * Then we iterate through the repository and if we find the user, we return it.
     * If we don't find the user, we return null.
     */
    @Override
    public User getUserByEmail(String email) {
        Iterable<User> it = userRepo.findAll();
//        for (User u: it){
//            if(u.getEmail().equals(email)){
//                return u;
//            }
//        }
//        return null;
        AtomicReference<User> found = new AtomicReference<>();
        it.forEach(u -> {
            if (u.getEmail().equals(email))
                found.set(u);
        });
        return found.get();
    }

    /**
     * creates a friendship between two users that have a pending request
     *
     * @param email1 - the email of the first user
     * @param email2 - the email of the second user
     */
    @Override
    public void acceptFriendship(String email1, String email2) {
        deleteFriendship(email1, email2);
        createFriendship(email1, email2);
    }

    /**
     * declines a friendship between two users that have a pending request
     *
     * @param email1 - the email of the first user
     * @param email2 - the email of the second user
     */
    @Override
    public void declineFriendRequest(String email1, String email2) {
        Optional f = null;
        User u1, u2;

        Predicate<Object> eNull = Objects::isNull;

        if (eNull.test(email1) || eNull.test(email2))
            throw new IllegalArgumentException("Emails must not be null!");

        u1 = getUserByEmail(email1);
        u2 = getUserByEmail(email2);

        if (eNull.test(u1) || eNull.test(u2) || u1.equals(u2)) {
            throw new ValidationException("Users must be not null and different!");
        }
        f = friendshipRepo.update(new FriendShip(u1, u2, REJECTED));

        if (f != null) {
            throw new IllegalArgumentException("Friendship already exists!");
        }


    }

    /**
     * creates a friendship request between two users
     *
     * @param email1 - the email of the first user
     * @param email2 - the email of the second user
     */
    @Override
    public void createFriendRequest(String email1, String email2) {
        Optional f = null;
        User u1, u2;

        Predicate<Object> eNull = Objects::isNull;


        if (eNull.test(email1) || eNull.test(email2))
            throw new IllegalArgumentException("Emails must not be null!");

        u1 = getUserByEmail(email1);
        u2 = getUserByEmail(email2);
        if (eNull.test(u1) || eNull.test(u2) || u1.equals(u2))
            throw new IllegalArgumentException("Users must be not null and different!");

        f = friendshipRepo.save(new FriendShip(u1, u2, PENDING));

        if (!eNull.test(f))
            throw new IllegalArgumentException("Friendship already exists!");


    }

    /**
     * Returns the number of friendships
     *
     * @return int - the number of friendships
     */
    public int getFriendshipCount() {
        return friendshipRepo.size();
    }

    /**
     * Returns the number of users
     *
     * @return int - the number of users
     */
    public int getUserCount() {
        return userRepo.size();
    }

    /**
     * Returns the number of friendships for a given user made in a certain month
     * @param email - the email of the user
     * @param month - the month
     * @return list - the list of friendships with that said criteria
     */
    public Iterable<FriendShip> getFriendshipsForUserFromMonth(String email, int month){
        Iterable<FriendShip> l = friendshipRepo.findAll();
        List<FriendShip> list = new ArrayList<>();
        l.forEach(f -> {
            if(f.getUser1().getEmail().equals(email) || f.getUser2().getEmail().equals(email)){
                if(f.getFriendsFrom().getMonthValue() == month)
                    list.add(f);
            }
        });
        return list;
    }


}

