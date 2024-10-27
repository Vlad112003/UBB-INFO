package ro.mpp.javaMPPServer.api;

import ro.mpp.javaMPPServer.domain.dto.MotorcycleRaceDTO;
import ro.mpp.javaMPPServer.services.MotorcycleRaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/motorcycle-race")
@CrossOrigin
public class MotorcycleRaceResource {
    @Autowired
    private MotorcycleRaceService motorcycleRaceService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addMotorcycleRace(@RequestBody MotorcycleRaceDTO motorcycleRace) throws Exception {
        try {
            return ResponseEntity.ok(motorcycleRaceService.addMotorcycleRace(motorcycleRace));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMotorcycleRace(@RequestBody MotorcycleRaceDTO motorcycleRace, @PathVariable UUID id) throws Exception{
        try {
            return ResponseEntity.ok(motorcycleRaceService.updateMotorcycleRace(motorcycleRace, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMotorcycleRace(@PathVariable UUID id) throws Exception {
        try {
            return ResponseEntity.ok(motorcycleRaceService.deleteMotorcycleRace(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findMotorcycleRace(@PathVariable UUID id) throws Exception {
        try {
            return ResponseEntity.ok(motorcycleRaceService.findMotorcycleRace(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllMotorcycleRaces() throws Exception {
        try {
            return ResponseEntity.ok(motorcycleRaceService.getAllMotorcycleRaces());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
