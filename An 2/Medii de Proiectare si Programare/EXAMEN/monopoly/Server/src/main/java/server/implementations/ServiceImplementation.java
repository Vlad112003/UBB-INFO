package server.implementations;

import model.Configuration;
import model.Game;
import model.Player;
import persistence.configuration.IConfigurationRepository;
import persistence.game.IGameRepository;
import persistence.player.IPlayerRepository;
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


        Random random = new Random();
        int[] x = new int[6];
        for (int i = 1; i <= 5; i++) {
            x[i] = random.nextInt(100) + 1;
        }
        Configuration configuration = new Configuration(x[1], x[2], x[3], x[4], x[5]);
        configurationRepository.add(configuration);
        Configuration conf = configurationRepository.getLastConfiguration();


        Game game = gameRepository.startGame(playerToLogin.getUsername(), 0,
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        game.setConf(conf.getId());
        gameRepository.updateGame(game);
        games.put(playerToLogin.getId(), game);
        configurations.put(playerToLogin.getId(), configuration);
        System.out.println(configuration);

        return playerToLogin;
    }

    @Override
    public Game getOneByUsername(String username) throws Exception {
        return gameRepository.getOneByUsername(username);
    }

    @Override
    public Configuration getConfigurationById(Integer id) throws Exception {
        return configurationRepository.getConfigurationById(id);
    }


    @Override
    public String choosePosition(int id, int n) {
        Configuration configuration = configurations.get(id);//fara findBy
        Game game = games.get(id);
        List<String> tries = game.getTries();
        int pct = game.getPct();
        int poz_curent = game.getPoz_curent();
        String output;
        //tries.add(String.valueOf(poz_curent));
        if (tries==null){
            tries=new ArrayList<>();
        }
        if (tries!=null && tries.size() >= 3 ) {
            return "Finished";
        }
        poz_curent += n;

        if (poz_curent>5) {
            //trece pe la start
            pct += 5;
        }
        poz_curent = poz_curent % 5 +1;
        String fct = "getPoz" + (poz_curent);
        int result = 0;
        try {
            // Get the method object using reflection
            Method method = configuration.getClass().getMethod(fct);

            // Invoke the method and get the result
            result = (Integer) method.invoke(configuration);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tries!=null && !tries.contains(String.valueOf(poz_curent))) {
            pct -= result;
        }
        //assert tries != null;
        tries.add(String.valueOf(poz_curent));
        game.setTries(tries);
        game.setPoz_curent(poz_curent);
        game.setPct(pct);
        gameRepository.updateGame(game);
        games.put(id, game);
        return "Next";
    }
//
//    @Override
//    public Collection<Game> getScores() {
//        Collection<Game> gamesList = gameRepository.getAll();
//        for (Game game1 : games.values()) {
//            gamesList.removeIf(game2 -> Objects.equals(game1.getId(), game2.getId()));
//        }
//        return gamesList;
//    }
//
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