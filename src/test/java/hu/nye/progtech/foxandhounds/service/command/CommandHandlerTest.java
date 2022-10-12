package hu.nye.progtech.foxandhounds.service.command;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.service.exception.InvalidMoveException;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class CommandHandlerTest {

    private static final char[][] MAP = {
            {'*', 'H', '*', 'H'},
            {'*', '*', '*', '*'},
            {'*', '*', '*', '*'},
            {'F', '*', '*', '*'},
    };

    private static final String[] HOUNDS = {"01", "03"};

    @Mock
    private MapVO MAP_VO = new MapVO(4, MAP, "30", HOUNDS);

    @Mock
    private MapPrinter mapPrinter;

    @Mock
    private Move move;

    @Mock
    private GameState gameState;

    @Mock
    private PrintWrapper printWrapper;

    @Mock
    private Player player = new Player(EXPECTED_NAME);

    private CommandHandler underTest;

    private static final String VALID_NAME_INPUT = "name Player";
    private static final String EXPECTED_NAME = "Player";
    private static final String INVALID_NAME_INPUT = "name";


    @BeforeEach
    public void setUp() {
        underTest = new CommandHandler(mapPrinter, move, gameState, printWrapper);
    }

    @Test
    public void testHandleCommandShouldPrintHelpWhenInputIsHelpCommand() {
        // when
        underTest.handleCommand("help");

        // then
        verify(printWrapper).printLine(anyString());
    }

    @Test
    public void testHandleCommandShouldCallPrintMapMethodWhenInputIsPrintCommand() {
        // when
        underTest.handleCommand("print");

        // then
        verify(mapPrinter).printMap(gameState.getCurrentMap());
    }

    @Test
    public void testHandleCommandSetGameOverWhenInputIsExitCommand() {
        // when
        underTest.handleCommand("exit");

        // then
        verify(printWrapper).printLine(anyString());
        verify(gameState).setGameOver(true);
    }

    @Test
    public void testHandleCommandShouldPrintDefaultMessageWhenInputIsDefaultCommand() {
        // when
        underTest.handleCommand("anything");

        // then
        verify(printWrapper).printLine("Unknown command");
    }

    @Test
    public void testHandleCommandShouldSetNameWhenInputIsNameCommand() {
        // given
        gameState = new GameState(MAP_VO, false, player);
        underTest = new CommandHandler(mapPrinter, move, gameState, printWrapper);
        // when
        underTest.handleCommand(VALID_NAME_INPUT);

        // then
        verify(printWrapper).printLine(anyString());
    }

    @Test
    public void testHandleCommandShouldCallMoveMethodsWhenInputIsMoveCommand() {
        // given
        gameState = new GameState(MAP_VO, false, player);
        underTest = new CommandHandler(mapPrinter, move, gameState, printWrapper);

        // when
        underTest.handleCommand("move 21");

        // then
        verify(move).foxMove(gameState, "21");
        verify(mapPrinter, times(2)).printMap(gameState.getCurrentMap());
        verify(move).enemyMove(gameState);
    }

    /*
    @Test
    public void testHandleCommandShouldThrowExceptionWhenInputIsInvalidMoveCommand() {
        // given
        doThrow(InvalidMoveException.class).when(underTest).handleCommand("move --");

        // when - then
        assertThrows(InvalidMoveException.class, () -> underTest.handleCommand("move --"));
    }
    */
}