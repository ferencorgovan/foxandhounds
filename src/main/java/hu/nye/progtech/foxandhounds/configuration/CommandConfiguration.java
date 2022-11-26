package hu.nye.progtech.foxandhounds.configuration;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.persistence.JdbcGameSavesRepository;
import hu.nye.progtech.foxandhounds.service.command.CommandHandler;
import hu.nye.progtech.foxandhounds.service.command.Move;
import hu.nye.progtech.foxandhounds.service.map.RandomGenerator;
import hu.nye.progtech.foxandhounds.service.validator.MoveValidator;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Java configuration class for command specific Spring Beans.
 */
@Configuration
public class CommandConfiguration {

    @Bean
    CommandHandler commandHandler(MapPrinter mapPrinter, Move move, GameState gameState,
                                  PrintWrapper printWrapper, JdbcGameSavesRepository jdbcGameSavesRepository) {
        return new CommandHandler(mapPrinter, move, gameState, printWrapper, jdbcGameSavesRepository);
    }

    @Bean
    Move move(MoveValidator moveValidator, PrintWrapper printWrapper, MapPrinter mapPrinter, RandomGenerator randomGenerator) {
        return new Move(moveValidator, printWrapper, mapPrinter, randomGenerator);
    }
}
