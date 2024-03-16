
import Domain.Utilizator;
import Domain.validators.UtilizatorValidator;
import Repository.InMemoryRepository;
import Service.ServiceUtilizator;

public class Main {

    public static void main(String[] args) {

        Utilizator u1=new Utilizator("u1FirstName", "u1LastName");
        u1.setId(1l);
        Utilizator u2=new Utilizator("u2FirstName", "u2LastName"); u2.setId(2l);
        Utilizator u3=new Utilizator("u3FirstName", "u3LastName"); u3.setId(3l);
        Utilizator u4=new Utilizator("u4FirstName", "u4LastName"); u4.setId(4l);
        Utilizator u5=new Utilizator("u5FirstName", "u5LastName"); u5.setId(5l);
        Utilizator u6=new Utilizator("u6FirstName", "u6LastName"); u6.setId(6l);
        Utilizator u7=new Utilizator("u7FirstName", "u7LastName"); u7.setId(7l);

        InMemoryRepository<Long, Utilizator> repo=new InMemoryRepository<>(new UtilizatorValidator());
        ServiceUtilizator service=new ServiceUtilizator(repo);
        service.addUtilizator(u1);
        service.addUtilizator(u2);
        service.addUtilizator(u3);

        System.out.println(service.size());

        service.deleteUtilizator(2l);

        System.out.println(service.size());

        for (Object u:service.getRepo()){
            System.out.println(u);
        }

        System.out.println("ok");
    }
}
