package persistence.cuvinte;

import java.util.List;

public interface ICuvinteRepository {
    Iterable<String> getRandomWords();
}
