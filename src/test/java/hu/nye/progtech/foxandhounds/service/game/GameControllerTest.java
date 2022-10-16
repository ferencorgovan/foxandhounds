package hu.nye.progtech.foxandhounds.service.game;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    private static final MapVO MAP_VO = new MapVO(0, null, null, null);

    @Mock
    private GameState gameState;

    @Mock
    private GameStepPerformer gameStepPerformer;

    @Mock
    private PrintWrapper printWrapper;

    private GameController underTest;

    @BeforeEach
    public void setUp() {
        underTest = new GameController(gameState, gameStepPerformer, printWrapper);
    }

    @Test
    public void testPlayGameShouldStopLoopWhenGameEnds() {
        // given
        given(gameState.isGameOver()).willReturn(true);

        // when
        underTest.playGame();

        // then
        verifyNoMoreInteractions(gameStepPerformer);
    }

    @Test
    public void testGameShouldContinueLoopWhenGameStepPerformerThrowsException() throws RuntimeException {
        // given
        doThrow(RuntimeException.class).when(gameStepPerformer).performGameStep();

        // when - then
        assertThrows(RuntimeException.class, () -> gameStepPerformer.performGameStep());

        verify(gameStepPerformer).performGameStep();
    }

    @Test
    public void testGameShouldContinueLoopUntilGameEnds() {
        // given
        given(gameState.isGameOver()).willReturn(false, true);

        // when
        underTest.playGame();

        // then
        verify(gameState, times(2)).isGameOver();
        verify(gameStepPerformer).performGameStep();
    }
}