package hu.nye.progtech.foxandhounds.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hu.nye.progtech.foxandhounds.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class for saving Fox and Hounds high score table.
 */
public class JdbcHighScoresRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcHighScoresRepository.class);
    static final String INSERT_STATEMENT = "INSERT INTO scores (name, wins) VALUES (?, 0);";

    static final String SELECT_STATEMENT = "SELECT * FROM scores;";

    static final String UPDATE_STATEMENT = "UPDATE scores SET WINS = WINS + 1 WHERE ID = (?)";

    static final String SELECT_ID_STATEMENT = "SELECT ID FROM scores WHERE NAME = (?);";

    private final Connection connection;

    public JdbcHighScoresRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Saves a player high score.
     *
     * @param player Current player
     */
    public void saveHighScore(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATEMENT)) {
            if (getPlayerId(player) == 0) {
                insertNewPlayer(player);
            }
            preparedStatement.setString(1, String.valueOf(getPlayerId(player)));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred", e);
        }
    }

    /**
     * Loads high score table.
     *
     * @return List of high scores
     */
    public List<String> loadHighScores() {
        List<String> highScoreList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_STATEMENT)) {
            while (resultSet.next()) {
                highScoreList.add(resultSet.getString("name") + "\t\t" + resultSet.getInt("wins"));
            }

        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred", e);
            throw new RuntimeException("Failed to load high scores");
        }
        return highScoreList;
    }

    private int getPlayerId(Player player) {
        int id = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_STATEMENT)) {
            preparedStatement.setString(1, player.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred", e);
        }
        return id;
    }

    private void insertNewPlayer(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STATEMENT)) {
            preparedStatement.setString(1, player.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred", e);
        }
    }

    public void close() throws Exception {
        connection.close();
    }
}
