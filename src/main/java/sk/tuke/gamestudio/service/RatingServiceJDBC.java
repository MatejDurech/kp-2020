package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import java.sql.*;

public class RatingServiceJDBC implements RatingService{
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "-J9j6f4Y";

    public static final String INSERT_RATE =
            "INSERT INTO rating (player, game, rating, ratedon) VALUES (?, ?, ?, ?)";

    public static final String SELECT_RATE =
            "SELECT rating FROM rating WHERE game = ? AND player = ? ORDER BY rating DESC LIMIT 5";

    public static final String SELECT_RATE_AVERAGE =
            "SELECT rating FROM rating WHERE game = ?";

    public static final String UPDATE=
            "UPDATE rating SET rating = ?, ratedon = ? WHERE player = ? AND game = ?;";



    @Override
    public void setRating(Rating rating) throws RatingException {
        if(getRating(rating.getGame(), rating.getPlayer()) == 0) {
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                try (PreparedStatement ps = connection.prepareStatement(INSERT_RATE)) {
                    ps.setString(1, rating.getPlayer());
                    ps.setString(2, rating.getGame());
                    ps.setInt(3, rating.getRating());
                    ps.setTimestamp(4, new Timestamp(rating.getRatedon().getTime()));
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RatingException("Error loading rating", e);
            }
        } else{
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
                    ps.setInt(1, rating.getRating());
                    ps.setTimestamp(2, new Timestamp(rating.getRatedon().getTime()));
                    ps.setString(3, rating.getPlayer());
                    ps.setString(4, rating.getGame());
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RatingException("Error loading rating", e);
            }
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        int rating;
        int help = 0;
        int counter = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = connection.prepareStatement(SELECT_RATE_AVERAGE)){
                ps.setString(1, game);
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        counter++;
                        rating = rs.getInt(1);
                        help += rating;
                    }
                    help = help / counter;
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Error loading rating", e);
        }
        return help;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try(PreparedStatement ps = connection.prepareStatement(SELECT_RATE)){
                ps.setString(1, game);
                ps.setString(2, player);
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                       return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Error loading rating", e);
        }
        return 0;
    }
}
