package implementations;

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

        Game game = gameRepository.startGame(playerToLogin.getUsername(), 0,
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        games.put(playerToLogin.getId(), game);

        return playerToLogin;
    }

    public Boolean win(List<String> tries){
        if (tries.contains("1 1 X") && tries.contains("1 2 X") && tries.contains("1 3 X"))
            return true;
        if (tries.contains("2 1 X") && tries.contains("2 2 X") && tries.contains("2 3 X"))
            return true;
        if (tries.contains("3 1 X") && tries.contains("3 2 X") && tries.contains("3 3 X"))
            return true;
        if (tries.contains("1 1 X") && tries.contains("2 2 X") && tries.contains("3 3 X"))
            return true;
        if (tries.contains("1 3 X") && tries.contains("2 2 X") && tries.contains("3 1 X"))
            return true;
        return false;
    }
    public Boolean loss(List<String> tries){
        if (tries.contains("1 1 O") && tries.contains("1 2 O") && tries.contains("1 3 O"))
            return true;
        if (tries.contains("2 1 O") && tries.contains("2 2 O") && tries.contains("2 3 O"))
            return true;
        if (tries.contains("3 1 O") && tries.contains("3 2 O") && tries.contains("3 3 O"))
            return true;
        if (tries.contains("1 1 O") && tries.contains("2 2 O") && tries.contains("3 3 O"))
            return true;
        if (tries.contains("1 3 O") && tries.contains("2 2 O") && tries.contains("3 1 O"))
            return true;
        return false;
    }

    private String calculeazaPozitieCalculator(List<String> tries){
        var poz="";
        Random random=new Random();
        while(poz==""){
            var i=random.nextInt(3)+1;
            var j=random.nextInt(3)+1;
            poz=i+" "+j;
            if(!tries.contains(poz+" X"))
                return poz+" O";
            if(!tries.contains(poz+" O"))
                return poz+" O";
            poz="";
        }
        return poz;
    }
    @Override
    public String choosePosition(int id, int x, int y) {
        Game game = games.get(id);
        List<String> tries = game.getTries();
        int pct=game.getPct();
        if(this.win(tries)==true){
            pct+=10;
            gameRepository.updateGame(game);
            games.put(id, game);
            return "CASTIG";}
        if(this.loss(tries)==true) {
            pct -= 10;
            gameRepository.updateGame(game);
            games.put(id, game);
            return "PIERD";
        }
        if(tries.size()==9) {
            return "REMIZA";
        }
        tries.add(x+" "+y+" X");
        var pozCalc=this.calculeazaPozitieCalculator(tries);
        System.out.println(pozCalc);
        tries.add(pozCalc);
        game.setTries(tries);
        game.setPct(pct);
        gameRepository.updateGame(game);
        games.put(id, game);
        return pozCalc;
    }

    @Override
    public Collection<Game> getScores() {
        Collection<Game> gamesList = gameRepository.getAll();
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

}
