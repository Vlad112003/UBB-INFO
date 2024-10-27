package persistence.configurations;

import model.Configuration;

public interface IConfigurationRepository {
    Configuration getRandomConfiguration();
    Configuration findById(int id);
    int add(Configuration configuration);
}
