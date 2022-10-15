package hu.nye.progtech.foxandhounds.service.command;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.service.RandomGenerator;
import hu.nye.progtech.foxandhounds.service.validator.MoveValidator;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MoveTest {

    @Mock
    private MoveValidator moveValidator;

    @Mock
    private PrintWrapper printWrapper;

    @Mock
    private MapPrinter mapPrinter;
    @Mock
    private Player player = new Player("Player");
    @Mock
    private RandomGenerator randomGenerator;
    @Mock
    private GameState gameState;

    private Move underTest;

    private static final String VALID_DESTINATION_COORDINATE = "23";
    private static final String WIN_COORDINATE = "01";
    private static final int MAP_LENGTH = 4;
    private static final int RANDOM_BOUND = MAP_LENGTH / 2;
    private static final int SELECTED_HOUND = 0;
    private static final char[][] MAP = {
            {'*', '*', '*', 'H'},
            {'H', '*', 'F', '*'},
            {'*', '*', '*', '*'},
            {'*', '*', '*', '*'},
    };

    private static final char[][] EXPECTED_MAP = {
            {'*', '*', '*', 'H'},
            {'H', '*', '*', '*'},
            {'*', '*', '*', 'F'},
            {'*', '*', '*', '*'},
    };

    private static final String[] HOUNDS = {"10", "03"};

    private static final String FOX_LOCATION = "12";

    private static final String EXPECTED_HOUND_ROW = "2";
    private static final String EXPECTED_HOUND_COLUMN = "1";

    private static final MapVO MAP_VO = new MapVO(4, MAP, "12", HOUNDS);

    private static final MapVO EXPECTED_MAP_VO = new MapVO(4, EXPECTED_MAP, VALID_DESTINATION_COORDINATE, HOUNDS);
    private static final String ENEMY_MOVE = "Enemy's turn: ";
    private static final String ENEMY_MOVE_LOG = "Enemy moves Hound #" + 1 + " to " + EXPECTED_HOUND_ROW + EXPECTED_HOUND_COLUMN;


    @Test
    public void testFoxMoveShouldPerformPlayerMove() {
        // given
        gameState = new GameState(MAP_VO, false, player);
        underTest = new Move(moveValidator, printWrapper, mapPrinter, randomGenerator);

        // when
        underTest.foxMove(gameState, VALID_DESTINATION_COORDINATE);

        // then
        verify(moveValidator).validateCoordinate(VALID_DESTINATION_COORDINATE);
        verify(moveValidator).validateMove(VALID_DESTINATION_COORDINATE, FOX_LOCATION);
        verify(moveValidator).validateFreeSpace(MAP_VO, VALID_DESTINATION_COORDINATE);

        assertEquals(gameState.getCurrentMap(), EXPECTED_MAP_VO);
    }

    @Test
    public void testFoxMoveShouldSetGameOverIfFoxHasWon() {
        // given
        underTest = new Move(moveValidator, printWrapper, mapPrinter, randomGenerator);
        gameState = new GameState(MAP_VO, false, player);

        // when
        underTest.foxMove(gameState, WIN_COORDINATE);

        // then
        assertTrue(gameState.isGameOver());
    }


    @Test
    void testEnemyMoveShouldPerformEnemyMove() {
        // given
        gameState = new GameState(MAP_VO, false, player);
        moveValidator = new MoveValidator(gameState, printWrapper);
        underTest = new Move(moveValidator, printWrapper, mapPrinter, randomGenerator);

        given(randomGenerator.makeRandomNumber(RANDOM_BOUND)).willReturn(SELECTED_HOUND);
        given(randomGenerator.makeRandomBool()).willReturn(true);

        // when
        underTest.enemyMove(gameState);

        // then
        verify(printWrapper).printLine(ENEMY_MOVE);
        verify(printWrapper).printLine(ENEMY_MOVE_LOG);
    }
}