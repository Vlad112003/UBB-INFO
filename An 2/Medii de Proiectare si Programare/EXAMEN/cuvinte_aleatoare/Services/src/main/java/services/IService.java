package services;

//import model.DTOFinishedGame;
//import model.Game;
import model.Configuration;
import model.Game;
import model.Player;

import java.util.Collection;

public interface IService {
    Player login(Player player, IObserver client) throws Exception;
    String choosePosition(int id, int x, int y) throws Exception;
    Collection<Game> getScores() throws Exception;
//    DTOFinishedGame getFinishedGameInfo(Integer id) throws Exception;
    Game getGameById(Integer id) throws Exception;
    Configuration getConfById(Integer id)throws Exception;
    Game getOneByUsername(String username )throws Exception;;
}