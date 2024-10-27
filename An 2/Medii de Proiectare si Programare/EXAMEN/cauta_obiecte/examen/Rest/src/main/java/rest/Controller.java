package rest;

import model.Configuration;
import model.Game;
import model.SUPERDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import persistence.configurations.ConfigurationRepository;
import persistence.games.GameRepository;

import java.util.Collection;

@CrossOrigin
@RestController
public class Controller {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @RequestMapping("/greeting")
    public String greeting() {
        return "Hello, World!";
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Game> getAll() {
        return gameRepository.getAll();
    }

    @RequestMapping(value = "/games/{username}", method = RequestMethod.GET)
    public Collection<Game> getByUsername(@PathVariable String username) {
        return gameRepository.getByUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Configuration create(@RequestBody Configuration configuration) {
        int id = configurationRepository.add(configuration);
        configuration.setId(id);
        return configuration;
    }

}