package hu.nye.progtech.foxandhounds.model;

import java.util.Objects;

public class GameState {
    private MapVO currentMap;
    private boolean gameOver;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public GameState(MapVO currentMap, boolean gameIsOver, Player name) {
        this.currentMap = currentMap;
        this.gameOver = gameIsOver;
        this.player = name;
    }

    public MapVO getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(MapVO currentMap) {
        this.currentMap = currentMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameState gameState = (GameState) o;
        return Objects.equals(currentMap, gameState.currentMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentMap);
    }
}
