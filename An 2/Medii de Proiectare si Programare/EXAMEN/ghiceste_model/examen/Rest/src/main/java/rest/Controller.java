package rest;

import model.Configuration;
import model.DTORest1;
import model.DTORest2;
import model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import persistence.configurations.ConfigurationRepository;
import persistence.games.GameRepository;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/model")
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

    @RequestMapping(value = "/conf/{idjoc}", method = RequestMethod.GET)    // http://localhost:8080/test/games/ion
    public DTORest1 getByUsername(@PathVariable int idjoc) {
        var game=this.gameRepository.getById(idjoc);
        var conf=this.configurationRepository.findById(game.getConf());
        DTORest1 dto=new DTORest1();
        dto.setIdConf(conf.getId());
        dto.setModel(conf.getModel());
        dto.setIdJoc(game.getId());
        return dto;
    }

    @RequestMapping(value = "/game/{idjoc}", method = RequestMethod.GET)    // http://localhost:8080/test/games/ion
    public DTORest2 getTries(@PathVariable int idjoc) {
        var game=this.gameRepository.getById(idjoc);
        DTORest2 dto=new DTORest2();
        dto.setTries(game.getTries());
        dto.setPct(game.getPct());
        dto.setUsername(game.getPlayer());
        dto.setIdJoc(game.getId());
        return dto;
    }
}
