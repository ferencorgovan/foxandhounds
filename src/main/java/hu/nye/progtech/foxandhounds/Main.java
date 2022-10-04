package hu.nye.progtech.foxandhounds;

import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.service.GameController;
import hu.nye.progtech.foxandhounds.service.MapGenerator;

public class Main {
    public static void main(String[] args) {
        MapGenerator mapGenerator = new MapGenerator();
        MapVO mapVO = mapGenerator.generateMap(8);
        GameController gameController = new GameController(mapVO);
        gameController.playGame();
    }
}
