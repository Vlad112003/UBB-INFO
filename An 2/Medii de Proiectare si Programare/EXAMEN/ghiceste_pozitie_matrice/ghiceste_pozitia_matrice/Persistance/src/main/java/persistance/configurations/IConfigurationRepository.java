package persistance.configurations;

import model.Configuration;

public interface IConfigurationRepository {
    Configuration getRandomConfiguration();
    int add(Configuration configuration);
}