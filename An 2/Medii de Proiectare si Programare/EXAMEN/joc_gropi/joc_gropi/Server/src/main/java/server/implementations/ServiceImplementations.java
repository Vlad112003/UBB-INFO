package server.implementations;

import model.Configuration;
import model.Game;
import model.Player;
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
    private final IGameRepository gameRepository;
    private final Map<Integer, IObserver> loggedPlayers;
    private final Map<Integer, Game> games;



    public ServiceImplementations(IPlayerRepository playerRepository, IGameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        loggedPlayers = new ConcurrentHashMap<>();
        games = new HashMap<>();

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
        var gr=this.generareGropi();
        Game game = gameRepository.startGame(playerToLogin.getUsername(),
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        game.setGropi(gr);
        System.out.println(gr+"GGRRRRRRRR");
        games.put(playerToLogin.getId(), game);
        gameRepository.updateGame(game);
//        configurations.put(playerToLogin.getId(), configuration);

        return playerToLogin;
    }

    public List<String> generareGropi(){
        List<String> gr=new ArrayList<>();
        Random random=new Random();
        for (var i=1;i<=4;i++){
            var j=random.nextInt(4)+1;  //TODO aici am modificat
            gr.add(i+" "+j+";");
        }
        int x= Integer.parseInt(String.valueOf(gr.get(0).charAt(0)));
        int y= Integer.parseInt(String.valueOf(gr.get(0).charAt(2)));
        String ultima=x+" "+y+";";
        while(gr.contains(ultima)){
            x= random.nextInt(4)+1;
            y= random.nextInt(4)+1;
            ultima=x+" "+y+";";
        }
        gr.add(ultima);
        return gr;
    }

    @Override
    public String choosePosition(int id, int x, int y) throws Exception {
        Game game = games.get(id);
        List<String> tries = game.getTries();
        tries.add(x + " " + y+";");
        game.setTries(tries);

        gameRepository.updateGame(game);
        games.put(id, game);

        if (tries.size() >= 4 && !game.getGropi().contains( x + " " + y+";")) {
                // GATA JOCU CASTIG
                return "WIN";
        }
        if (game.getGropi().contains(x + " " + y+";")) {
            // PIERDUT
            return "LOSS";
        }
        var pct=game.getPct();
        pct+=x;
        game.setPct(pct);
        gameRepository.updateGame(game);
        return "NEXT";
    }

    @Override
    public Game getGameByUsername(String username) throws Exception {
        return null;
    }

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