package hu.nye.progtech.foxandhounds.service.map;

import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.service.NumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component that generates a Fox and Hounds map.
 */
public class MapGenerator {

    private final NumberGenerator numberGenerator;

    private static final Logger LOGGER = LoggerFactory.getLogger(MapGenerator.class);

    public MapGenerator(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

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

        int foxIndex = numberGenerator.makeRandom(mapLength / 2);
        map[mapLength - 1][foxIndex * 2] = 'F';

        String[] houndsStart = new String[mapLength / 2];

        for (int i = 0; i < mapLength / 2; i++) {
            houndsStart[i] = "0" + (i + 1);
        }

        String foxStart = mapLength - 1 + String.valueOf(foxIndex * 2);
        for (int i = 0; i < mapLength / 2; i++) {
            map[0][i * 2 + 1] = 'H';
        }
        return new MapVO(mapLength, map, foxStart, houndsStart);
    }
}
