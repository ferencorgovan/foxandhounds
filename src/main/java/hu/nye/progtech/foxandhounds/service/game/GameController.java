package hu.nye.progtech.foxandhounds.service.game;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameController {
    private final GameState gameState;
    private final GameStepPerformer gameStepPerformer;
    private final PrintWrapper printWrapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    public GameController(GameState gameState, GameStepPerformer gameStepPerformer, PrintWrapper printWrapper) {
        this.gameState = gameState;
        this.gameStepPerformer = gameStepPerformer;
        this.printWrapper = printWrapper;
    }

    public void playGame() {
        LOGGER.info("Starting game");
        printWrapper.printLine("\nGame started! Type 'help' for commands.");
        while (!gameState.isGameOver()) {
            gameStepPerformer.performGameStep();
        }
    }
}
