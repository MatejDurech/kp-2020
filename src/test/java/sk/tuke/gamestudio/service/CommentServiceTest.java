package sk.tuke.gamestudio.service;

import org.junit.Test;
import sk.tuke.gamestudio.entity.Comment;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommentServiceTest {
    private CommentService commentService = new CommentServiceJDBC();


    @Test
    public void addComment() {
        Comment comment = new Comment("Zuzka", "mines", "ahoj", new Date());
        Comment comment1 = new Comment("Janko", "bejeweled", "topka", new Date());

        try {
            commentService.addComment(comment);
            commentService.addComment(comment1);

        } catch (CommentException e) {
            e.printStackTrace();
        }

        try {
            List<Comment> comments = commentService.getComments("mines");
            List<Comment> comments1 = commentService.getComments("bejeweled");

            assertEquals("Zuzka", comments.get(0).getPlayer());
            assertEquals("ahoj", comments.get(0).getComment());
            assertEquals("mines", comments.get(0).getGame());
            assertEquals("Janko", comments1.get(0).getPlayer());
            assertEquals("topka", comments1.get(0).getComment());
            assertEquals("bejeweled", comments1.get(0).getGame());

        } catch (CommentException e) {
            e.printStackTrace();
        }



    }
}
