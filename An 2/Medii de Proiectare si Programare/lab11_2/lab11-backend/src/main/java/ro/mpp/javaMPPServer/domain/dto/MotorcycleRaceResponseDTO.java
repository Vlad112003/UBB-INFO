package ro.mpp.javaMPPServer.domain.dto;

import java.util.UUID;

public record MotorcycleRaceResponseDTO(UUID id, String name, Integer engineCapacity, Integer participantsCount) {
}
