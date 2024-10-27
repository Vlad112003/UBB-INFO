package ro.mpp.javaMPPServer.domain;

import ro.mpp.javaMPPServer.domain.dto.MotorcycleRaceResponseDTO;

public record ResponseBody<T>(T body, String message) {}