package hu.nye.progtech.foxandhounds.service.validator;

import static org.junit.jupiter.api.Assertions.*;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.service.exception.InvalidCoordinateException;
import hu.nye.progtech.foxandhounds.service.exception.InvalidMoveException;
import hu.nye.progtech.foxandhounds.service.exception.OccupiedSpaceException;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MoveValidatorTest {

    private static final String VAlID_COORDINATE = "00";
    private static final String INVALID_COORDINATE = "55";
    private static final String FOX_LOCATION = "12";
    private static final String VALID_DESTINATION_COORDINATE = "21";
    private static final String INVALID_DESTINATION_COORDINATE = "22";
    private static final String OCCUPIED_SPACE = "01";

    private static final char[][] MAP = {
            {'*', 'H', '*', 'H'},
            {'*', '*', 'F', '*'},
            {'*', '*', '*', '*'},
            {'*', '*', '*', '*'},
    };

    private static final MapVO MAP_VO = new MapVO(4, MAP, null, null);

    @Mock
    private GameState gameState;

    @Mock
    private PrintWrapper printWrapper;

    private MoveValidator underTest;

    @BeforeEach
    public void setUp() {
        gameState = new GameState(MAP_VO, false, null);
        underTest = new MoveValidator(gameState, printWrapper);
    }

    @Test
    public void testIsValidCoordinateShouldReturnTrueWhenCoordinateIsNotOutOfBounds() {
        // when
        boolean result = underTest.isValidCoordinate(VAlID_COORDINATE);

        // then
        assertTrue(result);
    }

    @Test
    public void testIsValidCoordinateShouldReturnFalseWhenCoordinateIsOutOfBounds() {
        // when
        boolean result = underTest.isValidCoordinate(INVALID_COORDINATE);

        // then
        assertFalse(result);
    }

    @Test
    public void testIsValidMoveShouldReturnTrueWhenDestinationCoordinateIsDiagonallyOneSpaceAway() {
        // when
        boolean result = underTest.isValidMove(VALID_DESTINATION_COORDINATE, FOX_LOCATION);

        // then
        assertTrue(result);
    }

    @Test
    public void testIsValidMoveShouldReturnFalseWhenDestinationCoordinateIsNotDiagonallyOneSpaceAway() {
        // when
        boolean result = underTest.isValidMove(INVALID_DESTINATION_COORDINATE, FOX_LOCATION);

        // then
        assertFalse(result);
    }

    @Test
    public void testIsFreeShouldReturnTrueWhenDestinationCoordinateSpaceIsFree() {
        // when
        boolean result = underTest.isFree(MAP_VO, VALID_DESTINATION_COORDINATE);

        // then
        assertTrue(result);
    }

    @Test
    public void testIsFreeShouldReturnFalseWhenDestinationCoordinateSpaceIsNotFree() {
        // when
        boolean result = underTest.isFree(MAP_VO, OCCUPIED_SPACE);

        // then
        assertFalse(result);
    }

    @Test
    public void testValidateCoordinateShouldThrowInvalidCoordinateExceptionIfGivenCoordinateIsInvalid() {
        // when - then
        assertThrows(InvalidCoordinateException.class, () -> underTest.validateCoordinate(INVALID_COORDINATE));
    }

    @Test
    public void testValidateMoveShouldThrowInvalidMoveExceptionIfGivenMoveIsInvalid() {
        // when - then
        assertThrows(InvalidMoveException.class, () -> underTest.validateMove(INVALID_DESTINATION_COORDINATE, FOX_LOCATION));
    }

    @Test
    public void testValidateFreeSpaceShouldThrowOccupiedSpaceExceptionIfGivenCoordinateSpaceIsNotFree() {
        // when - then
        assertThrows(OccupiedSpaceException.class, () -> underTest.validateFreeSpace(MAP_VO, OCCUPIED_SPACE));
    }
    @Test
    public void testValidateCoordinateShouldNotThrowInvalidCoordinateExceptionIfGivenCoordinateIsValid() {
        // when - then
        underTest.validateCoordinate(VAlID_COORDINATE);
    }

    @Test
    public void testValidateMoveShouldNotThrowInvalidMoveExceptionIfGivenMoveIsValid() {
        // when - then
        underTest.validateMove(VALID_DESTINATION_COORDINATE, FOX_LOCATION);
    }

    @Test
    public void testValidateFreeSpaceShouldNotThrowOccupiedSpaceExceptionIfGivenCoordinateSpaceIsFree() {
        // when - then
        underTest.validateFreeSpace(MAP_VO, VALID_DESTINATION_COORDINATE);

    }
}