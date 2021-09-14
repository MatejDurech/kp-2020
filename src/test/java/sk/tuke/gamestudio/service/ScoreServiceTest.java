package sk.tuke.gamestudio.service;

import org.junit.Test;
import sk.tuke.gamestudio.entity.Score;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScoreServiceTest {
    private ScoreService service =  new ScoreServiceJDBC();
    //private ScoreService service = new ScoreServiceJDBC();

    @Test
    public void addScore() {
        Score score = new Score("Zuzka", "mines", 200, new Date());
        service.addScore(score);
        List<Score> scores = service.getBestScores("mines");

        assertEquals("Zuzka", scores.get(0).getPlayer());
        assertEquals(200, scores.get(0).getPoints());
        assertEquals("mines", scores.get(0).getGame());

        Score score1 = new Score("Tono", "jenga", 50000, new Date());
        service.addScore(score1);
        List<Score> scores1 = service.getBestScores("jenga");

        assertEquals("Tono", scores1.get(0).getPlayer());
        assertEquals(50000, scores1.get(0).getPoints());
        assertEquals("jenga", scores1.get(0).getGame());
    }
}
