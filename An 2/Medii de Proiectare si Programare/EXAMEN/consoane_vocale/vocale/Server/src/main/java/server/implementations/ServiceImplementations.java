package server.implementations;

import model.Configuration;
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

public class ServiceImplementations implements IService {
    private final IPlayerRepository playerRepository;
    private final IConfigurationRepository configurationRepository;
    private final IGameRepository gameRepository;
    private final Map<Integer, IObserver> loggedPlayers;
    private final Map<Integer, Game> games;
    private final Map<Integer, Configuration> configurations;



    public ServiceImplementations(IPlayerRepository playerRepository,IConfigurationRepository configurationRepository,IGameRepository gameRepository) {
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



        Configuration configuration = configurationRepository.getRandomConfiguration();
        Game game = gameRepository.startGame(playerToLogin.getUsername(),configuration.getCuvant(),configuration.getMasca(),
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        game.setPct(0);
//        System.out.println(gr+"GGRRRRRRRR");
        games.put(playerToLogin.getId(), game);
        gameRepository.updateGame(game);
        configurations.put(playerToLogin.getId(), configuration);
        System.out.println(configuration);
        return playerToLogin;
    }

    public boolean checkLettersInList(List<String> list, String word) {
        char[] letters = word.toCharArray();
        for (char letter : letters) {
            String letterString = String.valueOf(letter);
            boolean containsLetter = false;
            for (String element : list) {
                if (element.contains(letterString)) {
                    containsLetter = true;
                    break;
                }
            }
            if (!containsLetter) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String chooseLitera(int id, String litera) throws Exception {
        Configuration configuration = configurations.get(id);
        Game game = games.get(id);
        List<String> tries = game.getTries();
        String output;
        String word=game.getCuvant();
        int pct=game.getPct();
        String masca=game.getMasca();
        StringBuilder masca_noua = new StringBuilder();

        if (tries.size() >= 4 && !checkLettersInList(tries,word)) {
            // GATA JOC pierdut
//                gameRepository.updateGame(game);
//                games.put(id, game);
//            }
//            game.setMasca(word);
//            gameRepository.updateGame(game);
            return word;
        }
//        if (checkLettersInList(tries,word)) {
//            //CASTIGAT
//            game.setMasca(word);
//            gameRepository.updateGame(game);
//            return "da "+word;
//        }

        for (int i = 0; i < word.length(); i++) {
            if (Objects.equals(word.charAt(i), litera.charAt(0))) {
                pct++;
                masca_noua.append(litera.charAt(0));
            }
            else {
                masca_noua.append(masca.charAt(i));
            }
        }
        if(!word.contains(litera)) {
            pct-=2;
        }

        tries.add(litera);
        game.setTries(tries);
        game.setPct(pct);
        game.setMasca(String.valueOf(masca_noua));
        gameRepository.updateGame(game);
        games.put(id, game);

        if (checkLettersInList(tries,word)) // TODO GAME FINISHED
            return word;
        return String.valueOf(masca_noua);
    }

    @Override
    public Game getGame(int id) throws Exception {
        return games.get(id);
    }

    @Override
    public Game getGameByUsername(String username) throws Exception {
        return null;
    }

    //
//    @Override
//    public String choosePosition(int id, int x, int y) throws Exception {
//        Game game = games.get(id);
//        List<String> tries = game.getTries();
//        tries.add(x + " " + y+";");
//        game.setTries(tries);
//
//        gameRepository.updateGame(game);
//        games.put(id, game);
//
//        if (tries.size() >= 4 && !game.getGropi().contains( x + " " + y+";")) {
//                // GATA JOCU CASTIG
//                return "WIN";
//        }
//        if (game.getGropi().contains(x + " " + y+";")) {
//            // PIERDUT
//            return "LOSS";
//        }
//        var pct=game.getPct();
//        pct+=x;
//        game.setPct(pct);
//        gameRepository.updateGame(game);
//        return "NEXT";
//    }
//
//    @Override
//    public Game getGameByUsername(String username) throws Exception {
//        return null;
//    }
//
    @Override
    public Collection<Game> getScores() throws Exception {
        //        for (Game game1 : games.values()) {
//            gamesList.removeIf(game2 -> Objects.equals(game1.getId(), game2.getId()));
//        }
        return gameRepository.getAll();
    }

    @Override
    public Game getFinishedGameInfo(Integer id) throws Exception {
        Game game = games.get(id);
        games.remove(id);
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

        loggedPlayers.remove(id);

        return game;
    }


}