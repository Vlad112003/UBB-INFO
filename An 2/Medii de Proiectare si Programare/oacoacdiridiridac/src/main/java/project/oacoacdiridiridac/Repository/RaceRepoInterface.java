package project.oacoacdiridiridac.Repository;

import project.oacoacdiridiridac.Domain.*;

import java.util.UUID;

public interface RaceRepoInterface extends RepoInterface<UUID, Race> {

    Race getRaceByEngine(int engine);

    Race getRaceByOrganizer(String id_organizer);

}
