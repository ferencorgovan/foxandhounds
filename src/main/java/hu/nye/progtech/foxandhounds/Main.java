package hu.nye.progtech.foxandhounds;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import hu.nye.progtech.foxandhounds.configuration.ApplicationConfiguration;
import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.service.command.CommandHandler;
import hu.nye.progtech.foxandhounds.service.command.Move;
import hu.nye.progtech.foxandhounds.service.game.GameController;
import hu.nye.progtech.foxandhounds.service.game.GameStepPerformer;
import hu.nye.progtech.foxandhounds.service.input.InputReader;
import hu.nye.progtech.foxandhounds.service.map.MapGenerator;
import hu.nye.progtech.foxandhounds.service.map.RandomGenerator;
import hu.nye.progtech.foxandhounds.service.validator.MoveValidator;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Entry point of Fox and Hounds game.
 */
public class Main {
    /**
     * Entrypoint of the game.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("hu.nye.progtech.foxandhounds");

        GameController gameController = applicationContext.getBean(GameController.class);
        gameController.playGame();
    }
}
