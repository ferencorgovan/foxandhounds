package hu.nye.progtech.foxandhounds.service.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.service.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MapGeneratorTest {
    private static final int MAP_LENGTH = 4;

    @Mock
    private RandomGenerator numberGenerator;

    private static final char[][] EXPECTED_MAP = {
            {'*', 'H', '*', 'H'},
            {'*', '*', '*', '*'},
            {'*', '*', '*', '*'},
            {'F', '*', '*', '*'},
    };

    private static final String EXPECTED_FOX_START = "30";
    private static final String[] EXPECTED_HOUNDS = { "01", "03" };

    private static final MapVO EXPECTED = new MapVO(MAP_LENGTH, EXPECTED_MAP, EXPECTED_FOX_START, EXPECTED_HOUNDS);
    private static final int RANDOM_BOUND = MAP_LENGTH/2;

    private MapGenerator underTest;

    @BeforeEach
    public void setUp() {
        underTest = new MapGenerator(numberGenerator);
    }

    @Test
    public void testGenerateMapShouldReturnCorrectMap() {
        // given
        given(numberGenerator.makeRandomNumber(RANDOM_BOUND)).willReturn(0);

        // when
        MapVO result = underTest.generateMap(MAP_LENGTH);

        // then
        assertEquals(result, EXPECTED);
    }

}
