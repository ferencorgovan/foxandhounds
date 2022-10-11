package hu.nye.progtech.foxandhounds.ui;

import java.util.Map;

import hu.nye.progtech.foxandhounds.model.MapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapPrinter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapPrinter.class);

    public void printMap(MapVO mapVO) {
        LOGGER.info("Printing map");

        System.out.print("\n    ");
        for (int i = 0; i < mapVO.getMapLength(); i++) {
            System.out.print(i + " ");
        }
        System.out.print("\n   ");
        for (int i = 0; i < mapVO.getMapLength(); i++) {
            System.out.print("__");
        }
        System.out.println();
        for (int i = 0; i < mapVO.getMapLength(); i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < mapVO.getMapLength(); j++) {
                System.out.print(mapVO.getMap()[i][j] + " ");
            }
            System.out.println();
        }
    }
}
