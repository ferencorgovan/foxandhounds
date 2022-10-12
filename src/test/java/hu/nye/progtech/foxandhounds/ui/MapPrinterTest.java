package hu.nye.progtech.foxandhounds.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import hu.nye.progtech.foxandhounds.model.MapVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MapPrinterTest {

    private static final int MAP_LENGTH = 4;
    private static final char[][] MAP = {
            {'*', 'H', '*', 'H'},
            {'*', '*', '*', '*'},
            {'*', '*', '*', '*'},
            {'F', '*', '*', '*'},
    };

    private static final MapVO MAP_VO = new MapVO(MAP_LENGTH, MAP, null, null);

    @Mock
    private PrintWrapper printWrapper;

    @Captor
    private ArgumentCaptor<String> printWrapperArgumentCaptor;

    private MapPrinter underTest;

    @BeforeEach
    public void setUp() { underTest = new MapPrinter(printWrapper); }

    @Test
    public void testPrintMapShould () {

        // when
        underTest.printMap(MAP_VO);

        // then
        verify(printWrapper, times(MAP_LENGTH+1)).printLine(printWrapperArgumentCaptor.capture());
        verify(printWrapper, times(MAP_LENGTH*7+2)).print(printWrapperArgumentCaptor.capture());
    }
}
