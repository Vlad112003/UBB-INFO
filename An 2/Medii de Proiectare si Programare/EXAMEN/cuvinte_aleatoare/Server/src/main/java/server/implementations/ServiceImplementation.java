package server.implementations;

import model.Configuration;
import model.Game;
import model.Player;
import persistence.configurations.IConfigurationRepository;
import persistence.cuvinte.ICuvinteRepository;
import persistence.games.IGameRepository;
import persistence.players.IPlayerRepository;
import services.IObserver;
import services.IService;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImplementation implements IService {
    private final IPlayerRepository playerRepository;
    private final IConfigurationRepository configurationRepository;
    private final IGameRepository gameRepository;
    private final ICuvinteRepository cuvinteRepository;
    private final Map<Integer, IObserver> loggedPlayers;
    private final Map<Integer, Game> games;
    private final Map<Integer, Configuration> configurations;

    public ServiceImplementation(IPlayerRepository playerRepository, IConfigurationRepository configurationRepository,
                                 IGameRepository gameRepository, ICuvinteRepository cuvinteRepository) {
        this.cuvinteRepository=cuvinteRepository;
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

        List<String> cuvinte= (List<String>) cuvinteRepository.getRandomWords();
        System.out.println(cuvinte);
        List<String> doubledCuvinte = new ArrayList<>(cuvinte);
        doubledCuvinte.addAll(cuvinte);
        System.out.println(doubledCuvinte);
        Collections.shuffle(doubledCuvinte);
        Configuration configuration=new Configuration(doubledCuvinte.get(0),doubledCuvinte.get(1),doubledCuvinte.get(2),doubledCuvinte.get(3),doubledCuvinte.get(4),doubledCuvinte.get(5),doubledCuvinte.get(6),doubledCuvinte.get(7),doubledCuvinte.get(8),doubledCuvinte.get(9));
        configurationRepository.add(configuration);
        Configuration conf=configurationRepository.getLastConfiguration();
        Game game = gameRepository.startGame(playerToLogin.getUsername(),
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),0);

        game.setConf(conf.getId());
        gameRepository.updateGame(game);
        games.put(playerToLogin.getId(), game);
        configurations.put(playerToLogin.getId(), configuration);
        System.out.println(configuration);
        return playerToLogin;
    }



    @Override
    public String choosePosition(int id, int x, int y) {
        Configuration configuration = configurations.get(id);
        Game game = games.get(id);
        List<String> tries = game.getTries();
//        if(tries==null){
//            tries=new ArrayList<String>();
//        }
        String output;
//        if (tries.size() >= 10 ) {
//            //pierdut
//            return "Pierdut";
//        }
        String fctx="getPoz"+x;
        String fcty="getPoz"+y;
        String resultx="", resulty="";
        try {
            // Get the method object using reflection
            Method methodx = configuration.getClass().getMethod(fctx);
            Method methody = configuration.getClass().getMethod(fcty);

            // Invoke the method and get the result
            resultx = (String) methodx.invoke(configuration);
            resulty = (String) methody.invoke(configuration);

        } catch (Exception e) {
            e.printStackTrace();
        }
        int pct=game.getPct();
        tries.add(resultx+" "+resulty);
        game.setTries(tries);

        if (Objects.equals(resultx, resulty) && !Objects.equals(resultx, "")) {
            pct=pct-2;
            game.setPct(pct);
            gameRepository.updateGame(game);
            return "Equal";
        } else {
            pct=pct+3;
            game.setPct(pct);
            gameRepository.updateGame(game);
            return "Unequal";
        }
    }

    @Override
    public Collection<Game> getScores() {
        Collection<Game> gamesList = gameRepository.getAll();
        for (Game game1 : games.values()) {
            gamesList.removeIf(game2 -> Objects.equals(game1.getId(), game2.getId()));
        }
        return gamesList;
    }

    @Override
    public Game getGameById(Integer id) throws Exception {
        return gameRepository.getById(id);
    }

    @Override
    public Configuration getConfById(Integer id) throws Exception {
        return configurationRepository.findById(id);
    }

    @Override
    public Game getOneByUsername(String username) throws Exception {
        return gameRepository.getOneByUsername(username);
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
