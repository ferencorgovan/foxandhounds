package hu.nye.progtech.foxandhounds.configuration;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.service.validator.MoveValidator;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Java configuration class for Fox and Hounds map validation specific Spring Beans.
 */
@Configuration
public class ValidatorConfiguration {
    @Bean
    MoveValidator moveValidator(GameState gameState, PrintWrapper printWrapper) {
        return new MoveValidator(gameState, printWrapper);
    }
}
