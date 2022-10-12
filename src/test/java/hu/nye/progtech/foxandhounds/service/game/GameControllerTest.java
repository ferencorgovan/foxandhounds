package hu.nye.progtech.foxandhounds.service.game;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

import hu.nye.progtech.foxandhounds.model.GameState;
import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.model.Player;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Test
    public void testPlayGameShouldLoopGameUntilUserDoesNotExit() {
        // given
        gameState = new GameState(null, true, null);
        underTest = new GameController(gameState, gameStepPerformer, printWrapper);

        // when
        underTest.playGame();

        // then
        verifyNoInteractions(gameStepPerformer);
    }

    /*
    @Test
    public void testPlayGameShouldLoopGameUntilThePlayerDoesNotWin() {
        // given
        gameState = new GameState(MAP_VO, false, null);
        underTest = new GameController(gameState, gameStepPerformer, printWrapper);

        // when
        underTest.playGame();


        // then
        verify(gameStepPerformer).performGameStep();
    }
    */
}