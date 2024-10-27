package persistence.games;

import model.Game;

import java.util.Collection;

public interface IGameRepository {
    Game startGame(String player, String date, String time, Integer pct);
    void updateGame(Game game);
    Collection<Game> getAll();
    Collection<Game> getByUsername(String username);
    Game getById(Integer id);
    Game getOneByUsername(String username);
}