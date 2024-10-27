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
@RequestMapping("/cuvinte")
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

    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)    // http://localhost:8080/test/games/ion
    public DTORest getByUsername(@PathVariable Integer id) {
        //return gameRepository.getById(id);
        Game game=gameRepository.getById(id);
        Configuration conf=configurationRepository.findById(game.getConf());
        DTORest dto=new DTORest(game.getPlayer(),game.getTries(),conf, game.getPct());
        dto.setId(id);
        return dto;
    }

    @RequestMapping(value = "/configurations/{id}",method = RequestMethod.PUT)
    public Configuration add(@PathVariable int id, @RequestBody Configuration configuration) {
//        if(configuration.getId()==id) {
//            configurationRepository.updateConfiguration(configuration);
//        }
//        return configuration;
        Configuration existingConfiguration = configurationRepository.findById(id);
        if (existingConfiguration != null) {
            configuration.setId(existingConfiguration.getId());
            configurationRepository.updateConfiguration(configuration);
        }
        return configuration;

//        {
//            "poz1":"a",
//                "poz2":"b",
//                "poz3":"c",
//                "poz4":"d",
//                "poz5":"e",
//                "poz6":"a",
//                "poz7":"b",
//                "poz8":"c",
//                "poz9":"d",
//                "poz10":"e"
//
//        }
    }
}
