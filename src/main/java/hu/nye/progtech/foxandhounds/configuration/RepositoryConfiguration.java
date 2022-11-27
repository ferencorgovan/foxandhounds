package hu.nye.progtech.foxandhounds.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import hu.nye.progtech.foxandhounds.persistence.JdbcHighScoresRepository;
import hu.nye.progtech.foxandhounds.persistence.XmlGameSavesRepository;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Java configuration class for persistence layer specific Spring Beans.
 */
@Configuration
public class RepositoryConfiguration {

    @Bean
    Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/foxandhounds", "sa", "password");
    }

    @Bean(destroyMethod = "close")
    JdbcHighScoresRepository jdbcHighScoresRepository(Connection connection) {
        return new JdbcHighScoresRepository(connection);
    }

    @Bean
    XmlGameSavesRepository xmlGameSavesRepository(Marshaller marshaller, Unmarshaller unmarshaller) {
        return new XmlGameSavesRepository(marshaller, unmarshaller);
    }
}
