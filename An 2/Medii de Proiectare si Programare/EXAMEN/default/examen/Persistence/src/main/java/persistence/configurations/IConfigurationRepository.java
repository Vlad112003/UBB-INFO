package persistence.configurations;

import model.Configuration;

public interface IConfigurationRepository {
    Configuration getRandomConfiguration();
    int add(Configuration configuration);
    Configuration getLastConfiguration();
    void updateConfiguration(Configuration configuration);
    Configuration findById(Integer id);
}
