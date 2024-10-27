package server.implementations;

import model.Configuration;
import model.DTOFinishedGame;
import model.Game;
import model.Player;
import persistence.configurations.IConfigurationRepository;
import persistence.games.IGameRepository;
import persistence.players.IPlayerRepository;
import services.IObserver;
import services.IService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImplementation implements IService {
    IPlayerRepository playerRepository;
    IConfigurationRepository configurationRepository;
    IGameRepository gameRepository;
    private final Map<Integer, IObserver> loggedPlayers;
    private final Map<Integer, Game> games;
    private final Map<Integer, Configuration> configurations;
    private final Map<Integer, Integer> tries;


    public ServiceImplementation(IPlayerRepository playerRepository, IConfigurationRepository configurationRepository,
                                 IGameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.configurationRepository = configurationRepository;
        this.gameRepository = gameRepository;
        loggedPlayers = new ConcurrentHashMap<>();
        games = new ConcurrentHashMap<>();
        configurations = new ConcurrentHashMap<>();
        tries = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Player login(Player player, IObserver client) throws Exception {
        Player playerToLogin = playerRepository.findByUsername(player.getUsername());
        if (playerToLogin == null) {
            throw new Exception("Authentication failed.");
        }
        if (loggedPlayers.get(playerToLogin.getId()) != null) {
            throw new Exception("Player already logged in.");
        }
        loggedPlayers.put(playerToLogin.getId(), client);

        Configuration configuration = configurationRepository.getRandomConfiguration();

        Game game = gameRepository.startGame(playerToLogin.getUsername(),
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        games.put(playerToLogin.getId(), game);
        configurations.put(playerToLogin.getId(), configuration);
        tries.put(playerToLogin.getId(), 0);

        return playerToLogin;
    }

    @Override
    public synchronized void logout(Player player) {
        loggedPlayers.remove(player.getId());
    }

    @Override
    public synchronized Collection<Game> getScores() {
        Collection<Game> gamesList = gameRepository.getAll();
        for (Game game1 : games.values()) {
            gamesList.removeIf(game2 -> Objects.equals(game1.getId(), game2.getId()));
        }
        return gamesList;
    }

    @Override
    public synchronized String guess(int id, int guess) {
        Configuration configuration = configurations.get(id);
        Game game = games.get(id);
        List<String> found = game.getFound();

        if (found.size() == 3 || tries.get(id) == 4) {
            return "W";
        }

        tries.put(id, tries.get(id) + 1);

        if (configuration.getPosition1() == guess ||
                configuration.getPosition2() == guess ||
                configuration.getPosition3() == guess) {
            game.setScore(game.getScore() + 10);

            found.add(String.valueOf(guess));
            gameRepository.updateGame(game);
            games.put(id, game);
            if (found.size() == 3) {
                return "SW";
            }
            return "S";
        }

        int leftValue = 100;
        int rightValue = 100;
        if (configuration.getPosition1() < guess) {
            leftValue = guess - configuration.getPosition1();
        }
        if (configuration.getPosition2() < guess && guess - configuration.getPosition2() < leftValue) {
            leftValue = guess - configuration.getPosition2();
        }
        if (configuration.getPosition3() < guess && guess - configuration.getPosition3() < leftValue) {
            leftValue = guess - configuration.getPosition3();
        }
        if (configuration.getPosition1() > guess) {
            rightValue = configuration.getPosition1() - guess;
        }
        if (configuration.getPosition2() > guess && configuration.getPosition2() - guess < rightValue) {
            rightValue = configuration.getPosition2() - guess;
        }
        if (configuration.getPosition3() > guess && configuration.getPosition3() - guess < rightValue) {
            rightValue = configuration.getPosition3() - guess;
        }
        String output;
        if (leftValue < rightValue) {
            output = "L";
        } else if (leftValue > rightValue) {
            output = "R";
        } else {
            Random random = new Random();
            if (random.nextInt(2) == 0)
                output = "L";
            else {
                output = "R";
            }
        }
        game.setScore(game.getScore() - 1);
        gameRepository.updateGame(game);
        games.put(id, game);
        if (tries.get(id) == 4) {
            return output + "W";
        }
        return output;
    }

    @Override
    public synchronized DTOFinishedGame getFinishedGameInfo(Integer id) {
        Game game = games.get(id);
        Configuration configuration = configurations.get(id);
        DTOFinishedGame dtoFinishedGame = new DTOFinishedGame(game.getScore(),
                configuration.getPosition1() + ", " +
                        configuration.getPosition2() + ", " +
                        configuration.getPosition3() + ".");
        games.remove(id);
        configurations.remove(id);
        Collection<Game> gamesList = gameRepository.getAll();
        for (Game game1 : games.values()) {
            gamesList.removeIf(game2 -> Objects.equals(game1.getId(), game2.getId()));
        }

        for (IObserver loggedPlayer : loggedPlayers.values()) {
            try {
                loggedPlayer.gameFinished(gamesList);
            } catch (Exception e) {
                System.out.println("Error notifying player " + e.getMessage());
            }
        }

        return dtoFinishedGame;
    }
}