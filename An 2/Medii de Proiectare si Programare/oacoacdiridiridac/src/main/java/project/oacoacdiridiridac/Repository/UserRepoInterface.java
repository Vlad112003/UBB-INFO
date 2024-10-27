package project.oacoacdiridiridac.Repository;

import project.oacoacdiridiridac.Domain.User;

import java.util.UUID;

public interface UserRepoInterface extends RepoInterface<UUID, User> {
    User findByEmail(String email);
    Iterable<User> findByType(String type);

}
