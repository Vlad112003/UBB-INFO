package project.oacoacdiridiridac.Repository;

import project.oacoacdiridiridac.Domain.Team;

import java.util.UUID;

public interface TeamRepoInterface extends RepoInterface<UUID, Team>{
    Team getTeamByName(String name);

    Iterable<Team> getTeamByRacer(String id_user);

}
