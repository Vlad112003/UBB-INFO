package org.charity;

import org.charity.CharitableCaseRepository;
import org.charity.DonationRepository;
import org.charity.DonorRepository;
import org.charity.VolunteerRepository;
import org.charity.service.AuthenticationService;
import org.charity.service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class AppConfig {
    @Bean
    @Primary
    public Properties jbdsProperties() {
        Properties connectionProperties = new Properties();
        try(InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("app.properties")){
            if (input == null) {
                System.out.println("Sorry, unable to find app.properties");
                return null;
            }
            connectionProperties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return connectionProperties;
    }
    @Bean
    public VolunteerRepository volunteerRepository() {
        return new VolunteerRepository(jbdsProperties());
    }

    @Bean
    public DonorRepository donorRepository() {
        return new DonorRepository(jbdsProperties());
    }

    @Bean
    public CharitableCaseRepository caseRepository() {
        return new CharitableCaseRepository(jbdsProperties());
    }

    @Bean
    public DonationRepository donationRepository() {
        return new DonationRepository(jbdsProperties(), donorRepository(), caseRepository());
    }

    @Bean(name = "Service")
    public Service service() {
        return new Service(caseRepository(), donorRepository(), donationRepository(), volunteerRepository());
    }

    @Bean
    public AuthenticationService authenticationService() {
        return new AuthenticationService(volunteerRepository());
    }
}
