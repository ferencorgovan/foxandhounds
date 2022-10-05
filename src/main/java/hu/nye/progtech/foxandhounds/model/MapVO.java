package hu.nye.progtech.foxandhounds.model;

import java.util.Arrays;
import java.util.Objects;

public class MapVO {
    private final int mapLength;
    private final char[][] map;
    private String foxStart;
    private final String[] houndsStart;

    public String getFoxStart() {
        return foxStart;
    }

    public String[] getHoundsStart() {
        return houndsStart;
    }

    public void setFoxStart(String foxStart) {
        this.foxStart = foxStart;
    }

    public MapVO(int mapLength, char[][] map, String foxStart, String[] houndsStart) {
        this.mapLength = mapLength;
        this.map = deepCopy(map);
        this.foxStart = foxStart;
        this.houndsStart = houndsStart;
    }

    public int getMapLength() {
        return mapLength;
    }

    public char[][] getMap() {
        return deepCopy(map);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MapVO mapVO = (MapVO) o;
        return mapLength == mapVO.mapLength && Arrays.deepEquals(map, mapVO.map);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(mapLength);
        result = 31 * result + Arrays.deepHashCode(map);
        return result;
    }

    @Override
    public String toString() {
        return "MapVO{" +
                "mapLength=" + mapLength +
                ", map=" + Arrays.toString(map) +
                '}';
    }

    private char[][] deepCopy(char[][] array) {
        char[][] result = null;

        if (array != null) {
            result = new char[array.length][];
            for (int i = 0; i < array.length; i++) {
                result[i] = Arrays.copyOf(array[i], array[i].length);
            }
        }

        return result;
    }
}
