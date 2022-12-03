package hu.nye.progtech.foxandhounds.service.command;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.persistence.JdbcHighScoresRepository;
import hu.nye.progtech.foxandhounds.persistence.XmlGameSavesRepository;
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
    private JdbcHighScoresRepository jdbcHighScoresRepository;

    @Mock
    private XmlGameSavesRepository xmlGameSavesRepository;
    @Mock
    private Player player = new Player(EXPECTED_NAME);

    private CommandHandler underTest;

    private static final String VALID_NAME_INPUT = "name Player";
    private static final String INVALID_NAME_INPUT = "name";
    private static final String EXPECTED_NAME = "Player";

    private static final String INVALID_MOVE_COMMAND = "move --";
    private static final String VALID_MOVE_COMMAND = "move 21";
    private static final String VALID_COORDINATE = "21";
    private static final String PRINT_COMMAND = "print";
    private static final String EXIT_COMMAND = "exit";
    private static final String UNKNOWN_COMMAND = "anything";
    private static final String DEFAULT_COMMAND = "Unknown command";
    private static final String HIGH_SCORE_COMMAND = "hs";
    private static final String SAVE_COMMAND = "save";
    private static final String LOAD_COMMAND = "load";
    private static final String EXIT_PROMPT = "Exiting game";

    @BeforeEach
    public void setUp() {
        underTest = new CommandHandler(mapPrinter, move, gameState, printWrapper, jdbcHighScoresRepository, xmlGameSavesRepository);
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
        when(gameState.getPlayer()).thenReturn(player);

        // when
        underTest.handleCommand(VALID_NAME_INPUT);

        // then
        verify(gameState.getPlayer()).setName(EXPECTED_NAME);
        verify(printWrapper).printLine(anyString());
    }

    @Test
    public void testHandleCommandShouldPrintHighScoresWhenInputIsHighScoreCommand() {
        // when
        underTest.handleCommand(HIGH_SCORE_COMMAND);

        // then
        verify(printWrapper).printLine("NAME\t\tWINS");
        verify(jdbcHighScoresRepository).loadHighScores();
    }

    @Test
    public void testHandleCommandShouldSaveGameStateWhenInputIsSaveCommand() {
        // when
        underTest.handleCommand(SAVE_COMMAND);

        // then
        verify(xmlGameSavesRepository).saveGameState(gameState.getCurrentMap());
    }

    @Test
    public void testHandleCommandShouldLoadGameStateWhenInputIsLoadCommand() {
        // when
        underTest.handleCommand(LOAD_COMMAND);

        // then
        verify(xmlGameSavesRepository).loadGameState();
    }

    @Test
    public void testHandleCommandShouldCallMoveMethodsWhenInputIsMoveCommand() {
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
       when(gameState.isGameOver()).thenReturn(true);
        when(gameState.getPlayer()).thenReturn(player);
        when(gameState.getPlayer().getName()).thenReturn(EXPECTED_NAME);

        // when
        underTest.handleCommand(VALID_MOVE_COMMAND);

        // then
        verify(jdbcHighScoresRepository).saveHighScore(gameState.getPlayer());
        verify(printWrapper).printLine("\n" + gameState.getPlayer().getName() + " wins.");
        verifyNoMoreInteractions(gameState);
    }
}