package Domain;
import java.time.LocalDateTime;
import java.util.*;

public class Prietenie extends Entity<Tuple<Long,Long>>{

    LocalDateTime date;

    public Prietenie(Utilizator user1, Utilizator user2){
        super.setId(new Tuple<>(user1.getId(),user2.getId()));
        date = LocalDateTime.now();
    }

    public LocalDateTime getDate(){
        return date;
    }
}
