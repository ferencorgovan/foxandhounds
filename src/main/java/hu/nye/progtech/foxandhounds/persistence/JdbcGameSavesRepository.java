package hu.nye.progtech.foxandhounds.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import hu.nye.progtech.foxandhounds.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for saving Fox and Hounds high score table.
 */
public class JdbcGameSavesRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcGameSavesRepository.class);
    static final String INSERT_STATEMENT = "INSERT INTO scores (name, wins) VALUES (?, 0);";

    static final String SELECT_STATEMENT = "SELECT * FROM scores;";

    static final String UPDATE_STATEMENT = "UPDATE scores SET WINS = WINS + 1 WHERE ID = (?)";

    static final String SELECT_ID_STATEMENT = "SELECT ID FROM scores WHERE NAME = (?);";

    private final Connection connection;

    public JdbcGameSavesRepository(Connection connection) {
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

    public List<String> loadHighScores() {
        List<String> highScoreList = new ArrayList<>();
        highScoreList.add("NAME\t\tWINS");
        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_STATEMENT)) {
            while(resultSet.next()) {
                highScoreList.add(resultSet.getString("name") + "\t\t" + resultSet.getInt("wins"));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred", e);
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
