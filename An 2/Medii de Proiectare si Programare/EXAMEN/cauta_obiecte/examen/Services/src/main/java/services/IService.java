package services;

import model.DTOFinishedGame;
import model.Game;
import model.Player;

import java.util.Collection;

public interface IService {
    Player login(Player player, IObserver client) throws Exception;
    void logout(Player player);
    Collection<Game> getScores() throws Exception;
    String guess(int id, int guess);
    DTOFinishedGame getFinishedGameInfo(Integer id);
}