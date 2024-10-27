package rest;

import model.Configuration;
import model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import persistence.configurations.ConfigurationRepository;
import persistence.games.GameRepository;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/vocale")
public class Controller {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;
    // => port 8080
    //2023-06-20 21:39:19.181  INFO 25312 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)

    @RequestMapping("/greeting")
    public String greeting() {
        return "Hello, World!";
    }


    @RequestMapping(method = RequestMethod.GET)
    public Collection<Game> getAll() {
        return gameRepository.getAll();
    }

    @RequestMapping(value = "/games/{username}", method = RequestMethod.GET)    // http://localhost:8080/test/games/ion
    public Collection<Game> getByUsername(@PathVariable String username) {
        return gameRepository.getByUsername(username);
    }

    @RequestMapping(method = RequestMethod.POST)    // http://localhost:8080/test
    public Configuration create(@RequestBody Configuration configuration) {
        int id = configurationRepository.add(configuration);
        configuration.setId(id);
        return configuration;
    }
}
