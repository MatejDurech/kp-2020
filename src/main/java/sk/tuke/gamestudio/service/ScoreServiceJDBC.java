package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.sql.*;
import java.util.*;


public class ScoreServiceJDBC implements ScoreService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "-J9j6f4Y";

    public static final String INSERT_SCORE =
    "INSERT INTO score (player, game, points, playedon) VALUES (?, ?, ?, ?)";

    public static final String SELECT_SCORE =
        "SELECT player, game, points, playedon FROM score WHERE game = ? ORDER BY points DESC LIMIT 5;";


    @Override
    public void addScore(Score score) throws ScoreException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = connection.prepareStatement(INSERT_SCORE)){
                ps.setString(1, score.getPlayer());
                ps.setString(2, score.getGame());
                ps.setInt(3, score.getPoints());
                ps.setTimestamp(4, new Timestamp(score.getPlayedOn().getTime()));

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ScoreException("Error saving score", e);
        }
    }

    @Override
    public List<Score> getBestScores(String game) throws ScoreException {
        List<Score> scores = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = connection.prepareStatement(SELECT_SCORE)){
                ps.setString(1, game);
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        Score score = new Score(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getInt(3),
                                rs.getTimestamp(4)
                        );
                        scores.add(score);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ScoreException("Error loading score", e);
        }
        return scores;
    }

    public static void main(String[] args) throws Exception {
//        Score score = new Score("bejeweled", "jaro", 100, new java.util.Date());
//        Score score1 = new Score("bejeweled", "jaro", 150, new java.util.Date());
//        ScoreService scoreService = new ScoreServiceJDBC();
//        scoreService.addScore(score);
//        scoreService.addScore(score1);
//        System.out.println(scoreService.getBestScores("bejeweled"));
    }
}
