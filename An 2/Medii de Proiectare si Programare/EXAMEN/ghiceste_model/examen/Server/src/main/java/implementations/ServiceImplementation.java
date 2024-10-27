package implementations;

import model.Configuration;
import model.Game;
import model.Player;
import persistence.configurations.IConfigurationRepository;
import persistence.games.IGameRepository;
import persistence.players.IPlayerRepository;
import services.IObserver;
import services.IService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImplementation implements IService {
    private final IPlayerRepository playerRepository;
    private final IConfigurationRepository configurationRepository;
    private final IGameRepository gameRepository;
    private final Map<Integer, IObserver> loggedPlayers;
    private final Map<Integer, Game> games;
    private final Map<Integer, Configuration> configurations;

    public ServiceImplementation(IPlayerRepository playerRepository, IConfigurationRepository configurationRepository,
                                 IGameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.configurationRepository = configurationRepository;
        this.gameRepository = gameRepository;
        loggedPlayers = new ConcurrentHashMap<>();
        games = new HashMap<>();
        configurations = new HashMap<>();
    }

    @Override
    public Player login(Player player, IObserver client) throws Exception {
        Player playerToLogin = playerRepository.findByUsername(player.getUsername());
        if (playerToLogin == null) {
            throw new Exception("Authentication failed.");
        }
        if (loggedPlayers.get(playerToLogin.getId()) != null) {
            throw new Exception("Player already logged in.");
        }
        loggedPlayers.put(playerToLogin.getId(), client);

//        Configuration configuration = configurationRepository.getRandomConfiguration();
//
//        Game game = gameRepository.startGame(playerToLogin.getUsername(), configuration.getIndiciu(),
//                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
//                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//
//        games.put(playerToLogin.getId(), game);
//        configurations.put(playerToLogin.getId(), configuration);

        return playerToLogin;
    }

    private String calculateDistance(int x1, int y1, int x2, int y2) {
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return Double.toString(distance);
    }

    @Override
    public String choosePosition(int id, int x, int y) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        Configuration configuration = configurations.get(id);
//        Game game = games.get(id);
//        List<String> tries = game.getTries();
//        String output;
//        if (tries.size() >= 4 && !tries.contains("x:" + configuration.getX() + ", y: " + configuration.getY())) {
//            if (!Objects.equals(game.getIndiciu(), "")) {
//                game.setIndiciu("");
//                gameRepository.updateGame(game);
//                games.put(id, game);
//            }
//            return "Finished";
//        }
//        if (tries.contains("x:" + configuration.getX() + ", y: " + configuration.getY())) {
//            return "Finished";
//        }
//        if (configuration.getX() == x && configuration.getY() == y) {
//            output = configuration.getIndiciu();
//        } else {
//            output = calculateDistance(configuration.getX(), configuration.getY(), x, y);
//        }
//        tries.add("x:" + x + ", y: " + y);
//        game.setTries(tries);
//        gameRepository.updateGame(game);
//        games.put(id, game);
//        return output;
        return "";
    }

    @Override
    public Collection<Game> getScores() {
        Collection<Game> gamesList = gameRepository.getAll();
        for (Game game1 : games.values()) {
            gamesList.removeIf(game2 -> Objects.equals(game1.getId(), game2.getId()));
        }
        return gamesList;
    }

//    @Override
//    public DTOFinishedGame getFinishedGameInfo(Integer id) {
//        Game game = games.get(id);
//        System.out.println(games.get(id)+"AICIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        System.out.println(game.getId()+"AICIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA2");
//        Configuration configuration = configurations.get(id);
//        DTOFinishedGame dtoFinishedGame = new DTOFinishedGame(game.getTries().size(),
//                "x: " + configuration.getX() + ", y: " + configuration.getY());
//        games.remove(id);
//        configurations.remove(id);
//        Collection<Game> gamesList = gameRepository.getAll();
//        for (Game game1 : games.values()) {
//            gamesList.removeIf(game2 -> Objects.equals(game1.getId(), game2.getId()));
//        }
//
//        for (IObserver loggedPlayer : loggedPlayers.values()) {
//            try {
//                loggedPlayer.gameFinished(gamesList);
//            } catch (Exception e) {
//                System.out.println("Error notifying player " + e.getMessage());
//            }
//        }
//
//        loggedPlayers.remove(id);
//
//        return dtoFinishedGame;
//    }
}
