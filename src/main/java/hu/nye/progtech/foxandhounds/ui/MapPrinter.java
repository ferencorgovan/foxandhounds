package hu.nye.progtech.foxandhounds.ui;

import hu.nye.progtech.foxandhounds.model.MapVO;

public class MapPrinter {
    public void printMap(MapVO mapVO) {
        for (int i = 0; i < mapVO.getMapLength(); i++) {
            for (int j = 0; j < mapVO.getMapLength(); j++) {
                System.out.print(mapVO.getMap()[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
