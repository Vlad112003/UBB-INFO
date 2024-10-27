package services;

import model.Game;
import model.Player;

import java.util.Collection;
import java.util.List;

public interface IService {
    Player login(Player player, IObserver client) throws Exception;
    String choosePosition(int id, int x, int y) throws Exception;

    Game getGameByUsername(String username)throws Exception;

    Collection<Game> getScores() throws Exception;
    Game getFinishedGameInfo(Integer id) throws Exception;
}
