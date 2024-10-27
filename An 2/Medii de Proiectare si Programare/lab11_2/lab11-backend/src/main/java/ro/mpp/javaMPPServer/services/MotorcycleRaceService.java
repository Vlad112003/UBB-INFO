package ro.mpp.javaMPPServer.services;

import ro.mpp.javaMPPServer.domain.dto.MotorcycleRaceDTO;
import ro.mpp.javaMPPServer.domain.dto.MotorcycleRaceResponseDTO;
import ro.mpp.javaMPPServer.domain.models.MotorcycleRace;
import ro.mpp.javaMPPServer.exceptions.FieldEmpty;
import ro.mpp.javaMPPServer.exceptions.MotorcycleRaceNotFound;
import ro.mpp.javaMPPServer.repository.MotorcycleRaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

interface IMotorcycleRaceService {
    public MotorcycleRaceResponseDTO addMotorcycleRace(MotorcycleRaceDTO motorcycleRace);

    public MotorcycleRaceResponseDTO updateMotorcycleRace(MotorcycleRaceDTO motorcycleRace, UUID id);

    public Boolean deleteMotorcycleRace(UUID id);

    public Optional<MotorcycleRaceResponseDTO> findMotorcycleRace(UUID id);

    public List<MotorcycleRaceResponseDTO> getAllMotorcycleRaces();

}

@Service
public class MotorcycleRaceService implements IMotorcycleRaceService {
    @Autowired
    private MotorcycleRaceRepository motorcycleRaceRepository;

    @Override
    public MotorcycleRaceResponseDTO addMotorcycleRace(MotorcycleRaceDTO motorcycleRace) {
        if (motorcycleRace.name().isEmpty()) {
            throw new FieldEmpty("Motorcycle name is empty");
        }
        if (motorcycleRace.engineCapacity() < 0) {
            throw new FieldEmpty("Engine capacity cant be negative or 0");
        }
        if (motorcycleRace.participantsCount() < 0) {
            throw new FieldEmpty("The race cannot have negative number of participants!");
        }

        MotorcycleRace newRace = new MotorcycleRace(motorcycleRace.name(), motorcycleRace.engineCapacity(), motorcycleRace.participantsCount());
        motorcycleRaceRepository.save(newRace);
        return new MotorcycleRaceResponseDTO(
                newRace.getId(),
                newRace.getName(),
                newRace.getEngineCapacity(),
                newRace.getParticipantsCount()
        );
    }

    @Override
    public MotorcycleRaceResponseDTO updateMotorcycleRace(MotorcycleRaceDTO motorcycleRace, UUID id) {
        if (motorcycleRace.name().isEmpty()) {
            throw new FieldEmpty("Motorcycle name is empty");
        }
        if (motorcycleRace.engineCapacity() < 0) {
            throw new FieldEmpty("Engine capacity cant be negative or 0");
        }
        if (motorcycleRace.participantsCount() < 0) {
            throw new FieldEmpty("The race cannot have negative number of participants!");
        }
        MotorcycleRace updatedRace = motorcycleRaceRepository.findById(id)
                .orElseThrow(() -> new MotorcycleRaceNotFound("Motorcycle Race not found"));
        updatedRace.setName(motorcycleRace.name());
        updatedRace.setEngineCapacity(motorcycleRace.engineCapacity());
        updatedRace.setParticipantsCount(motorcycleRace.participantsCount());
        motorcycleRaceRepository.save(updatedRace);
        return new MotorcycleRaceResponseDTO(
                updatedRace.getId(),
                updatedRace.getName(),
                updatedRace.getEngineCapacity(),
                updatedRace.getParticipantsCount()
        );
    }

    @Override
    public Boolean deleteMotorcycleRace(UUID id) {
        MotorcycleRace toDeleteRace = motorcycleRaceRepository.findById(id)
                .orElseThrow(() -> new MotorcycleRaceNotFound("Motorcycle Race not found"));
        motorcycleRaceRepository.delete(toDeleteRace);
        return true;
    }

    public Optional<MotorcycleRaceResponseDTO> findMotorcycleRace(UUID id) {
        Optional<MotorcycleRace> motorcycleRaceOptional = Optional.ofNullable(motorcycleRaceRepository.findById(id)
                .orElseThrow(() -> new MotorcycleRaceNotFound("Motorcycle Race not found")));;
        if (motorcycleRaceOptional.isPresent()) {
            MotorcycleRace motorcycleRace = motorcycleRaceOptional.get();
            return Optional.of(new MotorcycleRaceResponseDTO(
                    motorcycleRace.getId(),
                    motorcycleRace.getName(),
                    motorcycleRace.getEngineCapacity(),
                    motorcycleRace.getParticipantsCount()
            ));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MotorcycleRaceResponseDTO> getAllMotorcycleRaces() {
        return motorcycleRaceRepository.findAll().stream()
                .map(motorcycleRace -> new MotorcycleRaceResponseDTO(
                        motorcycleRace.getId(),
                        motorcycleRace.getName(),
                        motorcycleRace.getEngineCapacity(),
                        motorcycleRace.getParticipantsCount()
                ))
                .collect(Collectors.toList());
    }
}
