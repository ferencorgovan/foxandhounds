package hu.nye.progtech.foxandhounds.service;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameController {
    private final GameState gameState;
    private final GameStepPerformer gameStepPerformer;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    public GameController(GameState gameState, GameStepPerformer gameStepPerformer) {
        this.gameState = gameState;
        this.gameStepPerformer = gameStepPerformer;
    }

    public void playGame() {
        LOGGER.info("Starting game");
        System.out.println("\nGame started! Type 'help' for commands.");
        while (!gameState.isGameOver()) {
            gameStepPerformer.performGameStep();
        }
    }
}
