package lab13.iss1.Service;

import lab13.iss1.Domain.*;
import lab13.iss1.Repository.GameDB;
import lab13.iss1.Repository.OrderDB;
import lab13.iss1.Repository.UserDB;

import java.util.ArrayList;
import java.util.UUID;

public class Service {
    private UserDB userDB;
    private GameDB gameDB;
    private OrderDB orderDB;

    public Service(UserDB userDB, GameDB gameDB, OrderDB orderDB) {
        this.userDB = userDB;
        this.gameDB = gameDB;
        this.orderDB = orderDB;
    }

    public User login(String email, String password){
        return userDB.logUser(email, password);
    }

    public boolean signup(String email, String username, String password){
        User user = new User(email, username, password);
        if (userDB.save(user).equals(user))
            return true;
        return false;
    }

    public boolean addGame(String title, String genre, float price){
        Game game = new Game(title, genre, price);
        return gameDB.save(game).equals(game);
    }

    public void deleteGame(String title){
        gameDB.delete(title);
    }

    public void updateGame(String title, String genre, float price){
        Game game = new Game(title, genre, price);
        gameDB.update(game);
    }

    public void addOrder(String email, UUID gameID){
        Order order = new Order(email, gameID);
        orderDB.save(order);
    }

    public void deleteOrder(UUID id){
        orderDB.delete(id);
    }

    public ArrayList<Game> getAllGames(){
        return gameDB.findAll();
    }

    public ArrayList<Order> getAllOrders(){
        return orderDB.findAll();
    }

    public Game getGame(String name){
        return gameDB.findOne(name);
    }

    public void purchaseGame(String email, UUID gameId){
        addOrder(email, gameId);
    }

    public void approveOrder(UUID id) {
        Order order = orderDB.findOne(id);
        order.setStatus(OrderStatus.ACCEPTED);
        orderDB.update(order);
    }

    public void declineOrder(UUID id) {
        Order order = orderDB.findOne(id);
        order.setStatus(OrderStatus.DECLINED);
        orderDB.update(order);
    }

    public ArrayList<Game> getGamesOwnedByUser(String email){
        ArrayList<Order> orders = orderDB.findAll();
        ArrayList<Game> games = new ArrayList<>();
        for (Order order : orders){
            if (order.getEmail().equals(email) && order.getStatus().equals(OrderStatus.ACCEPTED)){
                games.add(gameDB.findByUUID(order.getGameID()));
            }
        }
        return games;
    }

    //the id is the game id, not the order id
    public void installGame(String email, UUID gameID) {
        Game game = gameDB.findByUUID(gameID);
        if (game.getStatus().equals(GameStatus.NOT_INSTALLED)){
            Order order = orderDB.findByGameId(gameID);
            if (order.getEmail().equals(email) && order.getStatus().equals(OrderStatus.ACCEPTED)){
                game.setStatus(GameStatus.INSTALLED);
                gameDB.update(game);
            }
        }

    }

    public void uninstallGame(String email, UUID gameId) {
        Game game = gameDB.findByUUID(gameId);
        if (game.getStatus().equals(GameStatus.INSTALLED)){
            Order order = orderDB.findByGameId(gameId);
            if (order.getEmail().equals(email) && order.getStatus().equals(OrderStatus.ACCEPTED)){
                game.setStatus(GameStatus.NOT_INSTALLED);
                gameDB.update(game);
            }
        }
    }
}
