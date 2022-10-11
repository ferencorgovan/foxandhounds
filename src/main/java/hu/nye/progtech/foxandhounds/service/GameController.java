package hu.nye.progtech.foxandhounds.service;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;

public class GameController {
    MapVO mapVO;
    GameState gameState;
    GameStepPerformer gameStepPerformer;

    public GameController(GameState gameState, MapVO mapVO, GameStepPerformer gameStepPerformer) {
        this.gameState = gameState;
        this.mapVO = mapVO;
        this.gameStepPerformer = gameStepPerformer;
    }

    public void playGame() {
        System.out.println("\nGame started! Type 'help' for commands.");
        while (!gameState.isGameOver()) {
            gameStepPerformer.performGameStep();
        }
    }
}
