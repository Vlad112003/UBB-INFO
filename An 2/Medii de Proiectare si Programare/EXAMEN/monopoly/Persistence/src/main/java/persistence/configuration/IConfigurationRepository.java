package persistence.configuration;

import model.Configuration;

public interface IConfigurationRepository {
    Configuration getRandomConfiguration();
    int add(Configuration configuration);
    Configuration getLastConfiguration();

    Configuration getConfigurationById(Integer id);

}
