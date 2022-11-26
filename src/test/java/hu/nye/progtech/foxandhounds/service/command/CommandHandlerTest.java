package hu.nye.progtech.foxandhounds.service.command;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.persistence.JdbcGameSavesRepository;
import hu.nye.progtech.foxandhounds.service.exception.InvalidMoveException;
import hu.nye.progtech.foxandhounds.service.exception.InvalidNameException;
import hu.nye.progtech.foxandhounds.ui.MapPrinter;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
    private JdbcGameSavesRepository jdbcGameSavesRepository;
    @Mock
    private Player player = new Player(EXPECTED_NAME);

    private CommandHandler underTest;

    private static final String VALID_NAME_INPUT = "name Player";
    private static final String INVALID_NAME_INPUT = "name";
    private static final String EXPECTED_NAME = "Player";

    private static final String INVALID_MOVE_COMMAND = "move --";
    private static final String VALID_MOVE_COMMAND = "move 21";
    private static final String VALID_COORDINATE = "21";
    private static final String HELP_COMMAND = "help";
    private static final String PRINT_COMMAND = "print";
    private static final String EXIT_COMMAND = "exit";
    private static final String UNKNOWN_COMMAND = "anything";
    private static final String DEFAULT_COMMAND = "Unknown command";
    private static final String EXIT_PROMPT = "Exiting game";

    @BeforeEach
    public void setUp() {
        underTest = new CommandHandler(mapPrinter, move, gameState, printWrapper, jdbcGameSavesRepository);
    }

    @Test
    public void testHandleCommandShouldPrintHelpWhenInputIsHelpCommand() {
        // when
        underTest.handleCommand(HELP_COMMAND);

        // then
        verify(printWrapper).printLine(anyString());
    }

    @Test
    public void testHandleCommandShouldCallPrintMapMethodWhenInputIsPrintCommand() {
        // when
        underTest.handleCommand(PRINT_COMMAND);

        // then
        verify(mapPrinter).printMap(gameState.getCurrentMap());
    }

    @Test
    public void testHandleCommandSetGameOverWhenInputIsExitCommand() {
        // when
        underTest.handleCommand(EXIT_COMMAND);

        // then
        verify(printWrapper).printLine(EXIT_PROMPT);
        verify(gameState).setGameOver(true);
    }

    @Test
    public void testHandleCommandShouldPrintDefaultMessageWhenInputIsDefaultCommand() {
        // when
        underTest.handleCommand(UNKNOWN_COMMAND);

        // then
        verify(printWrapper).printLine(DEFAULT_COMMAND);
    }

    @Test
    public void testHandleCommandShouldSetNameWhenInputIsNameCommand() {
        // given
        gameState = new GameState(MAP_VO, false, player);
        underTest = new CommandHandler(mapPrinter, move, gameState, printWrapper, jdbcGameSavesRepository);

        // when
        underTest.handleCommand(VALID_NAME_INPUT);

        // then
        verify(printWrapper).printLine(anyString());
    }

    @Test
    public void testHandleCommandShouldCallMoveMethodsWhenInputIsMoveCommand() {
        // given
        gameState = new GameState(MAP_VO, false, player);
        underTest = new CommandHandler(mapPrinter, move, gameState, printWrapper, jdbcGameSavesRepository);

        // when
        underTest.handleCommand(VALID_MOVE_COMMAND);

        // then
        verify(move).foxMove(gameState, VALID_COORDINATE);
        verify(move).enemyMove(gameState);
    }

    @Test
    public void testHandleCommandShouldThrowExceptionWhenInputIsInvalidMoveCommand() {
        // when - then
        assertThrows(InvalidMoveException.class, () -> underTest.handleCommand(INVALID_MOVE_COMMAND));
    }

    @Test
    public void testHandleCommandShouldThrowExceptionWhenInputIsInvalidNameCommand() {
        // when - then
        assertThrows(InvalidNameException.class, () -> underTest.handleCommand(INVALID_NAME_INPUT));
    }

    @Test
    public void testHandleCommandShouldExitGameWhenPlayerWins() {
        // given
        gameState = new GameState(null, true, player);
        underTest = new CommandHandler(mapPrinter, move, gameState, printWrapper, jdbcGameSavesRepository);

        // when
        underTest.handleCommand(VALID_MOVE_COMMAND);

        // then
        verify(printWrapper).printLine("\n" + gameState.getPlayer().getName() + " wins.");
    }
}