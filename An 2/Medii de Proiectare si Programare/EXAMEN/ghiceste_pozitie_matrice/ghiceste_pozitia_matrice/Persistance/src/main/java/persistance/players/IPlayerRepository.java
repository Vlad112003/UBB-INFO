package persistance.players;

import model.Player;

public interface IPlayerRepository {
    Player findByUsername(String username);
}
