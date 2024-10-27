package project.kidscompetitions.Repository.interfaces;

import project.kidscompetitions.Domain.Participant;
import project.kidscompetitions.Domain.SignUp;

public interface ISignUpRepository extends IRepository<Long, SignUp>{
    Iterable<Participant> findAllSignUps(Long idTest);
}
