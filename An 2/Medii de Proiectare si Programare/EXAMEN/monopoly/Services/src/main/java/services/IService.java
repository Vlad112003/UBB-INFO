package services;

import model.Configuration;
import model.Game;
import model.Player;

import java.util.Collection;

public interface IService {
    Player login(Player player, IObserver client) throws Exception;
    Game getOneByUsername(String username )throws Exception;

    Configuration getConfigurationById(Integer id) throws Exception;

    String choosePosition(int id, int n)throws Exception;
    //String choosePosition(int id, int x, int y) throws Exception;
//    Collection<Game> getScores() throws Exception;
//    DTOFinishedGame getFinishedGameInfo(Integer id) throws Exception;
}
