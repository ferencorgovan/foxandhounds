package hu.nye.progtech.foxandhounds.ui;

import hu.nye.progtech.foxandhounds.model.MapVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapPrinter {
    private final PrintWrapper printWrapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MapPrinter.class);

    public MapPrinter(PrintWrapper printWrapper) {
        this.printWrapper = printWrapper;
    }

    public void printMap(MapVO mapVO) {
        LOGGER.info("Printing map");

        printWrapper.print("\n    ");
        for (int i = 0; i < mapVO.getMapLength(); i++) {
            printWrapper.print(i + " ");
        }
        printWrapper.print("\n   ");
        for (int i = 0; i < mapVO.getMapLength(); i++) {
            printWrapper.print("__");
        }
        printWrapper.printLine("");
        for (int i = 0; i < mapVO.getMapLength(); i++) {
            printWrapper.print(i + " | ");
            for (int j = 0; j < mapVO.getMapLength(); j++) {
                printWrapper.print(mapVO.getMap()[i][j] + " ");
            }
            printWrapper.printLine("");
        }
    }
}
