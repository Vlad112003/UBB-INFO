package persistance.games;

import model.Game;

import java.util.Collection;

public interface IGameRepository {
    Game startGame(String player, String indiciu, String date, String time);
    void updateGame(Game game);
    Collection<Game> getAll();
    Collection<Game> getByUsername(String username);
}