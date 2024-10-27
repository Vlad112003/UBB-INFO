package ro.mpp.javaMPPServer.repository;

import ro.mpp.javaMPPServer.domain.models.MotorcycleRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface MotorcycleRaceRepository extends JpaRepository<MotorcycleRace, UUID>, JpaSpecificationExecutor<MotorcycleRace> {
}
