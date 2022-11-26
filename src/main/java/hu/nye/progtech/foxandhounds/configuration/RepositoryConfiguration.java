package hu.nye.progtech.foxandhounds.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import hu.nye.progtech.foxandhounds.persistence.JdbcGameSavesRepository;
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
    JdbcGameSavesRepository jdbcGameSavesRepository(Connection connection) {
        return new JdbcGameSavesRepository(connection);
    }
}
