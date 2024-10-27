package persistence.configurations;

import model.Configuration;
import model.Game;

import java.util.List;

public interface IConfigurationRepository {
    Configuration getRandomConfiguration();
    int add(Configuration configuration);
    Configuration getLastConfiguration();
    void updateConfiguration(Configuration configuration);
    Configuration findById(Integer id);


}
