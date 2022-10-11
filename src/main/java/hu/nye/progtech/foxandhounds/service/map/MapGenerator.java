package hu.nye.progtech.foxandhounds.service.map;

import java.util.Random;

import hu.nye.progtech.foxandhounds.model.MapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component that generates a Fox and Hounds map.
 */
public class MapGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapGenerator.class);

    /**
     * Generates game map.
     *
     * @param mapLength Length of a game map.
     * @return the generated map
     */
    public MapVO generateMap(int mapLength) {

        LOGGER.info("Generating map");
        char[][] map = new char[mapLength][mapLength];
        for (int i = 0; i < mapLength; i++) {
            for (int j = 0; j < mapLength; j++) {
                map[i][j] = '*';
            }
        }

        Random random = new Random();
        int foxIndex = random.nextInt(mapLength / 2);
        map[mapLength - 1][foxIndex * 2] = 'F';

        String[] houndsStart = new String[4];
        houndsStart[0] = "01";
        houndsStart[1] = "03";
        houndsStart[2] = "05";
        houndsStart[3] = "07";
        String foxStart = "7" + String.valueOf(foxIndex * 2);
        for (int i = 0; i < mapLength / 2; i++) {
            map[0][i * 2 + 1] = 'H';
        }
        return new MapVO(mapLength, map, foxStart, houndsStart);
    }

}
