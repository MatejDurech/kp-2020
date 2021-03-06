package sk.tuke.gamestudio.service;

import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

public class RatingServiceRestClient implements RatingService {
    private static final String URL = "http://localhost:8080/api/rating";

    private RestTemplate restTemplate = new RestTemplate();
    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForEntity(URL, rating, Rating.class);
    }
    @Override
    public int getRating(String game, String player) throws RatingException {
        return restTemplate.getForObject(URL + "/" + game + "/" + player , int.class);
    }
    @Override
    public int getAverageRating(String game) throws RatingException {
        return restTemplate.getForObject(URL + "/" + game, int.class);
    }

}
