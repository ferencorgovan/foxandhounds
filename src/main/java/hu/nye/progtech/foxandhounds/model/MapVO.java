package hu.nye.progtech.foxandhounds.model;

import java.util.Arrays;
import java.util.Objects;

public class MapVO {
    private final int mapLength;
    private final char[][] map;


    public MapVO(int mapLength, char[][] map) {
        this.mapLength = mapLength;
        this.map = deepCopy(map);
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
