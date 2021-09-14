package sk.tuke.gamestudio.service;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.gamestudio.entity.Rating;
import static org.aspectj.runtime.internal.Conversions.intValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        int rate = getRating(rating.getGame(), rating.getPlayer());

        if (rate == 0) {
            entityManager.persist(rating);
        } else {
            entityManager.createNamedQuery("Rating..update").setParameter("player", rating.getPlayer()).setParameter("game", rating.getGame()).setParameter("ratedon", rating.getRatedon()).setParameter("rating", rating.getRating()).executeUpdate();
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return intValue (entityManager.createNamedQuery("Rating.getAverageRating").setParameter("game", game).getSingleResult());
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try{
            return intValue (entityManager.createNamedQuery("Rating.getRating").setParameter("game", game).setParameter("player", player).getSingleResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
