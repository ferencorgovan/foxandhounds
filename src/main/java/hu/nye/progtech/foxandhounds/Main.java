package hu.nye.progtech.foxandhounds;

import java.sql.SQLException;

import hu.nye.progtech.foxandhounds.service.game.GameController;
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
