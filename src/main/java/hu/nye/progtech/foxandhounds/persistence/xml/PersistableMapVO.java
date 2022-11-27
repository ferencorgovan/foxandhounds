package hu.nye.progtech.foxandhounds.persistence.xml;

import java.util.Arrays;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Copy of MapVO for XML saves.
 */
@XmlRootElement
public class PersistableMapVO {

    private int mapLength;
    private char[][] map;
    private String foxStart;
    private String[] houndsStart;


    public PersistableMapVO() {
    }

    public PersistableMapVO(int mapLength, char[][] map, String foxStart, String[] houndsStart) {
        this.mapLength = mapLength;
        this.map = map;
        this.foxStart = foxStart;
        this.houndsStart = houndsStart;
    }

    public int getMapLength() {
        return mapLength;
    }

    public void setMapLength(int mapLength) {
        this.mapLength = mapLength;
    }

    public char[][] getMap() {
        return map;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public String getFoxStart() {
        return foxStart;
    }

    public void setFoxStart(String foxStart) {
        this.foxStart = foxStart;
    }

    public String[] getHoundsStart() {
        return houndsStart;
    }

    public void setHoundsStart(String[] houndsStart) {
        this.houndsStart = houndsStart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersistableMapVO that = (PersistableMapVO) o;
        return mapLength == that.mapLength && Arrays.deepEquals(map, that.map) &&
                Objects.equals(foxStart, that.foxStart) && Arrays.equals(houndsStart, that.houndsStart);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(mapLength, foxStart);
        result = 31 * result + Arrays.deepHashCode(map);
        result = 31 * result + Arrays.hashCode(houndsStart);
        return result;
    }
}
