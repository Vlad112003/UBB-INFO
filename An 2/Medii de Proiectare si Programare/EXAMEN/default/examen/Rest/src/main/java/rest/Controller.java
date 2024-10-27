package rest;

import model.Configuration;
import model.DTORest;
import model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import persistence.configurations.ConfigurationRepository;
import persistence.games.GameRepository;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/examen")
public class Controller {
    @Autowired
    private GameRepository gameRepository;

    @RequestMapping("/greeting")
    public String greeting() {
        return "Hello, World!";
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Game> getAll() {
        return gameRepository.getAll();
    }

    @RequestMapping(value = "/games/{username}", method = RequestMethod.GET)    // http://localhost:8080/examen/games/ion
    public Collection<Game> getByUsernameOrdered(@PathVariable String username) {
        return gameRepository.getByUsernameOrdered(username);
    }

    @RequestMapping(value = "/incercari/{id}",method = RequestMethod.PUT)       //http://localhost:8080/examen/incercari/3  cu put
    public Game add(@PathVariable int id, @RequestBody String pozitii) {
        Game existingGame = gameRepository.getById(id);
        if (existingGame != null) {
            var tries=existingGame.getTries();
            tries.add(pozitii+" X");
            existingGame.setTries(tries);
            gameRepository.updateGame(existingGame);
            return existingGame;

        }
        return null;
    }
}
