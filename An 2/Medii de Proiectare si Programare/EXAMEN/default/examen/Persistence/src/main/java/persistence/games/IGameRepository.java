package persistence.games;

import model.Game;

import java.util.Collection;

public interface IGameRepository {
    Game startGame(String player,Integer pct, String date, String time);
    void updateGame(Game game);
    Collection<Game> getAll();
    Collection<Game> getByUsername(String username);
    Collection<Game> getByUsernameOrdered(String username);
    Game getById(Integer id);
    Game getOneByUsername(String username);
}