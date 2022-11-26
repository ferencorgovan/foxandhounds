package hu.nye.progtech.foxandhounds.persistence;

import hu.nye.progtech.foxandhounds.model.Player;
import org.h2.jdbc.JdbcCallableStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JdbcGameSavesRepositoryTest {

    private JdbcGameSavesRepository underTest;

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Player player;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        underTest = new JdbcGameSavesRepository(connection);
    }

    @Test
    void testSaveHighScoreShouldInsertNewPlayerAndHighScoreWhenThereIsNoException() throws SQLException {
        // given
        when(connection.prepareStatement(JdbcGameSavesRepository.UPDATE_STATEMENT)).thenReturn(preparedStatement);
        when(connection.prepareStatement(JdbcGameSavesRepository.SELECT_ID_STATEMENT)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(JdbcGameSavesRepository.INSERT_STATEMENT)).thenReturn(preparedStatement);

        // when
        underTest.saveHighScore(player);

        // then
        verify(connection).prepareStatement(JdbcGameSavesRepository.UPDATE_STATEMENT);
        verify(preparedStatement, times(3)).setString(1, player.getName());
        verify(preparedStatement, times(2)).executeQuery();
        verify(preparedStatement, times(2)).executeUpdate();
        verify(preparedStatement).setString(1, "0");
        verify(preparedStatement, times(4)).close();
        verifyNoMoreInteractions(connection, statement, preparedStatement);
    }

    @Test
    void testSaveShouldUpdateHighScoreWhenPlayerIsInDatabaseAndThereIsNoException() throws SQLException {
        when(connection.prepareStatement(JdbcGameSavesRepository.UPDATE_STATEMENT)).thenReturn(preparedStatement);
        when(connection.prepareStatement(JdbcGameSavesRepository.SELECT_ID_STATEMENT)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(2);
        // when
        underTest.saveHighScore(player);

        // then
        verify(connection).prepareStatement(JdbcGameSavesRepository.UPDATE_STATEMENT);
        verify(preparedStatement, times(2)).setString(1, player.getName());
        verify(preparedStatement, times(2)).executeQuery();
        verify(preparedStatement).executeUpdate();
        verify(preparedStatement, times(3)).close();
        //verifyNoMoreInteractions(connection, statement, preparedStatement);
    }

    @Test
    public void testCloseShouldDelegateCloseCallToConnection() throws Exception {
        // when
        underTest.close();

        // then
        verify(connection).close();
        verifyNoMoreInteractions(connection);
    }
}