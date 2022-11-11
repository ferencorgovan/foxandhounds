package hu.nye.progtech.foxandhounds.configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.service.command.CommandHandler;
import hu.nye.progtech.foxandhounds.service.game.GameController;
import hu.nye.progtech.foxandhounds.service.game.GameStepPerformer;
import hu.nye.progtech.foxandhounds.service.input.InputReader;
import hu.nye.progtech.foxandhounds.service.map.MapGenerator;
import hu.nye.progtech.foxandhounds.service.map.RandomGenerator;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Java configuration class for generic application related Spring Beans.
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    GameController gameController(GameState gameState, GameStepPerformer gameStepPerformer, PrintWrapper printWrapper) {
        return new GameController(gameState, gameStepPerformer, printWrapper);
    }

    @Bean
    GameStepPerformer gameStepPerformer(InputReader inputReader, CommandHandler commandHandler, PrintWrapper printWrapper) {
        return new GameStepPerformer(inputReader, commandHandler, printWrapper);
    }

    @Bean
    GameState gameState(RandomGenerator randomGenerator) {
        Player player = new Player("Player");
        MapGenerator mapGenerator = new MapGenerator(randomGenerator);
        MapVO mapVO = mapGenerator.generateMap(8);
        return new GameState(mapVO, false, player);
    }

    @Bean
    InputReader inputReader() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        return new InputReader(bufferedReader);
    }

    @Bean
    MapPrinter mapPrinter(PrintWrapper printWrapper) {
        return new MapPrinter(printWrapper);
    }

    @Bean
    RandomGenerator randomGenerator() {
        return new RandomGenerator();
    }

    @Bean
    PrintWrapper printWrapper() {
        return new PrintWrapper();
    }
}
