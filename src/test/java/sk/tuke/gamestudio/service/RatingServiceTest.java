package sk.tuke.gamestudio.service;

import org.junit.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class RatingServiceTest {
    private RatingService ratingService = new RatingServiceJDBC();
    @Test
    public void addRating() {
        Rating rating = new Rating("Zuzka", "mines", 1, new Date());
        Rating rating1 = new Rating("Janko", "bejeweled", 3, new Date());
        Rating rating2 = new Rating("Hrasko", "bejeweled", 1, new Date());

            try {
                ratingService.setRating(rating);
            } catch (RatingException e) {
                e.printStackTrace();
            }
            try {
               ratingService.setRating(rating1);
           } catch (RatingException e) {
               e.printStackTrace();
           }
        try {
            ratingService.setRating(rating2);
        } catch (RatingException e) {
            e.printStackTrace();
        }


        try {

            assertEquals(1,ratingService.getRating("mines", "Zuzka"));
            assertEquals(3,ratingService.getRating("bejeweled", "Janko"));
            assertEquals(2,ratingService.getAverageRating("bejeweled"));


        } catch (RatingException e) {
            e.printStackTrace();
        }


    }
}
