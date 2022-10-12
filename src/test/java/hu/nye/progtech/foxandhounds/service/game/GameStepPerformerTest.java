package hu.nye.progtech.foxandhounds.service.game;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import hu.nye.progtech.foxandhounds.service.command.CommandHandler;
import hu.nye.progtech.foxandhounds.service.input.InputReader;
import hu.nye.progtech.foxandhounds.ui.PrintWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameStepPerformerTest {

    private static final String INPUT = "input";
    private static final String PROMPT = "\nEnter a command: ";
    @Mock
    private InputReader inputReader;

    @Mock
    private CommandHandler commandHandler;

    @Mock
    private PrintWrapper printWrapper;

    private GameStepPerformer underTest;

    @BeforeEach
    public void setUp() {
        underTest = new GameStepPerformer(inputReader, commandHandler, printWrapper);
    }

    @Test
    public void testPerformGameStepShouldReadUserInputAndCallCommandHandler() {
        // given
        given(inputReader.readInput()).willReturn(INPUT);

        // when
        underTest.performGameStep();

        // then
        verify(printWrapper).print(PROMPT);
        verify(inputReader).readInput();
        verify(commandHandler).handleCommand(INPUT);
    }
}