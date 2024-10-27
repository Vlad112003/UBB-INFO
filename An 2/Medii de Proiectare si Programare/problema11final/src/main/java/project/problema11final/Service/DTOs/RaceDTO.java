package project.problema11final.Service.DTOs;

import java.util.Objects;

public class RaceDTO {
    private String engineCapacity;
    private String noRacers;

    @Override
    public String toString() {
        return "RaceDTO{" +
                "engineCapacity='" + engineCapacity + '\'' +
                ", noRacers='" + noRacers + '\'' +
                '}';
    }

    public RaceDTO(String engineCapacity, String noRacers) {
        this.engineCapacity = engineCapacity;
        this.noRacers = noRacers;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getNoRacers() {
        return noRacers;
    }

    public void setNoRacers(String noRacers) {
        this.noRacers = noRacers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RaceDTO raceDTO = (RaceDTO) o;
        return noRacers == raceDTO.noRacers && Objects.equals(engineCapacity, raceDTO.engineCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(engineCapacity, noRacers);
    }
}
