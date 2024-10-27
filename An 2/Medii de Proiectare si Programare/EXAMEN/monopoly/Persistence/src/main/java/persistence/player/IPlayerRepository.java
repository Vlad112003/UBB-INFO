package persistence.player;

import model.Player;

public interface IPlayerRepository {
    Player findByUsername(String username);
}
