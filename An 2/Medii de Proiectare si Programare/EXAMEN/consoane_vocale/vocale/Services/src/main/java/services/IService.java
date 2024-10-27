package services;

//import model.Game;
import model.Game;
import model.Player;

import java.util.Collection;

public interface IService {
    Player login(Player player, IObserver client) throws Exception;
    String chooseLitera(int id, String litera) throws Exception;
    Game getGame(int id) throws Exception;

    Game getGameByUsername(String username)throws Exception;

    Collection<Game> getScores() throws Exception;
    Game getFinishedGameInfo(Integer id) throws Exception;
}
