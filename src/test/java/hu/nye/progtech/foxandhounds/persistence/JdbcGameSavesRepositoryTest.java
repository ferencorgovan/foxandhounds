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
import java.util.ArrayList;
import java.util.List;

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

    private static final String NAME = "F";
    private static final int WINS = 2;
    @BeforeEach
    public void setUp() {
        underTest = new JdbcGameSavesRepository(connection);
    }

    @Test
    public void testSaveHighScoreShouldInsertNewPlayerAndHighScoreWhenThereIsNoException() throws SQLException {
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
    public void testSaveShouldUpdateHighScoreWhenPlayerIsInDatabaseAndThereIsNoException() throws SQLException {
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
    public void testLoadHighScoresShouldReturnHighScoreListWhenThereIsNoException() throws SQLException {
        // given
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(JdbcGameSavesRepository.SELECT_STATEMENT)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getInt("wins")).thenReturn(WINS);
        List<String> expected = List.of(NAME + "\t\t" + WINS);

        // when
        List<String> result = underTest.loadHighScores();

        // then
        verify(connection).createStatement();
        verify(statement).executeQuery(JdbcGameSavesRepository.SELECT_STATEMENT);
        verify(resultSet, times(2)).next();
        verify(resultSet).getString("name");
        verify(resultSet).getInt("wins");
        verify(statement).close();
        verify(resultSet).close();
        verifyNoMoreInteractions(connection);
        assertEquals(expected, result);
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