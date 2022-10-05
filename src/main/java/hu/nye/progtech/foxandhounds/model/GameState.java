package hu.nye.progtech.foxandhounds.model;

import java.util.Objects;

public class GameState {
    private MapVO currentMap;

    public GameState(MapVO currentMap) {
        this.currentMap = currentMap;
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
