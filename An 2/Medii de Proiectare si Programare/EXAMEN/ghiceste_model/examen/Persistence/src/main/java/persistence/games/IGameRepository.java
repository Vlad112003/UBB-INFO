package persistence.games;

import model.Game;

import java.util.Collection;

public interface IGameRepository {
    Game startGame(String player, int conf, String date, String time);
    void updateGame(Game game);
    Collection<Game> getAll();
    Collection<Game> getByUsername(String username);
    Game getById(int id);
}