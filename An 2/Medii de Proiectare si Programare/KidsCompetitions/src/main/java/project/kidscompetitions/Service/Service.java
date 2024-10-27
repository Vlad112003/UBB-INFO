package project.kidscompetitions.Service;

import project.kidscompetitions.Domain.Competition;
import project.kidscompetitions.Domain.Employee;
import project.kidscompetitions.Domain.Participant;
import project.kidscompetitions.Domain.SignUp;
import project.kidscompetitions.Repository.CompetitionRepository;
import project.kidscompetitions.Repository.EmployeeRepository;
import project.kidscompetitions.Repository.ParticipantRepository;
import project.kidscompetitions.Repository.SignUpRepository;

import java.util.Optional;

public class Service {

    private CompetitionRepository competitionRepository;
    private SignUpRepository signUpRepository;
    private EmployeeRepository employeeRepository;
    private ParticipantRepository participantRepository;

    public Service(CompetitionRepository repo, SignUpRepository repo2, EmployeeRepository repo3, ParticipantRepository repo4) {
        this.competitionRepository = repo;
        this.signUpRepository = repo2;
        this.employeeRepository = repo3;
        this.participantRepository = repo4;
    }

    public Iterable<Competition> getAllCompetitions()
    {
        return this.competitionRepository.findAll();
    }
    public Iterable<Participant> findAllSignUps(Long idTest)
    {
        return this.signUpRepository.findAllSignUps(idTest);
    }
    Optional<Competition> getOne(Long id){
        return Optional.empty();
    }

    public Optional<Employee> findUser(String user, String pass) {
        Optional<Employee> result= this.employeeRepository.findOneByUsername(user);
        if( result.isPresent() && result.get().getPassword().equals(pass))
            return result;
        return  Optional.empty();
    }

    public Iterable<Participant> getAllClients() {
        return this.participantRepository.findAll();
    }

    public Optional<SignUp> signUpParticipant(long idParticipant, long idCompetition)
    {
        Optional<Participant> participant = this.participantRepository.findOne(idParticipant);
        Optional<Competition> athleticTests = this.competitionRepository.findOne(idCompetition);
        SignUp signUp=new SignUp(participant.get(), athleticTests.get());
        return this.signUpRepository.save(signUp);
    }
}
