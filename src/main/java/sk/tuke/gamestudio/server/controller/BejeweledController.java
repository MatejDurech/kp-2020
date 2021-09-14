package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.bejeweled.core.Field;
import sk.tuke.gamestudio.game.bejeweled.core.GameState;
import sk.tuke.gamestudio.game.bejeweled.core.Move;
import sk.tuke.gamestudio.service.*;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/bejeweled")
public class BejeweledController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserController userController;

    private Field field = new Field(5);


    private Integer i1;
    private Integer i2;
    private Integer i3;
    private Integer i4;
    private int timeForLevel;

     public BejeweledController(){
         newGame();
     }

    @RequestMapping("/new/")
     private String newGame(){
         field.generate();
         field.setScore(0);
         timeForLevel = 60000;
         field.setGameState(GameState.GAME_OVER);
         return "redirect:/bejeweled";
     }

    @RequestMapping("/start/")
    private String startGame(){
        field.setGameState(GameState.PLAYING);
        field.currentTime();
        return "redirect:/bejeweled";
    }



    @RequestMapping
    public String bejeweled(Model model) throws CommentException, RatingException {
        prepareModel(model);
        return "bejeweled";
    }


    @RequestMapping("/click/{index1}/{index2}")
    public String click(@PathVariable Integer index1, @PathVariable Integer index2,  Model model) throws CommentException {

        if (field.getGameTime() == GameState.TIME_OK && field.getGameState() == GameState.PLAYING) {
            if (i1 == null && i2 == null) {
                i1 = index2;
                i2 = index1;
            } else if (i3 == null && i4 == null) {
                i3 = index2;
                i4 = index1;
            }
            if (i1 != null && i3 != null) {
                field.test();
                Move move = new Move(field);
                move.moveTile(new int[]{i1, i2, i3, i4});
                field.control();
                i1 = null;
                i2 = null;
                i3 = null;
                i4 = null;
            }
            field.time(timeForLevel);
        }
        else if (field.getGameState() == GameState.PLAYING){
            scoreService.addScore(new Score(userController.getLoggedUser(), "bejeweled", field.getScore(), new Date()));
            field.setGameState(GameState.GAME_OVER);
            return "redirect:/bejeweled";
        }
        return "redirect:/bejeweled";
    }

    @PostMapping("/fooform")
    public String foobarPost(@ModelAttribute("command") FormCommand command, Model model ) throws CommentException {
        commentService.addComment(new Comment(userController.getLoggedUser(), "bejeweled", command.getTextField(), new Date()));
        return "redirect:/bejeweled";
    }

    @RequestMapping("/{number}")
    public String rate(Model model, @PathVariable Integer number) throws RatingException {
        ratingService.setRating(new Rating(userController.getLoggedUser(), "bejeweled", number, new Date()));
        return "redirect:/bejeweled";
    }


    private void prepareModel(Model model) throws CommentException, RatingException {
        model.addAttribute("scores", scoreService.getBestScores("bejeweled"));
        model.addAttribute("comment", commentService.getComments("bejeweled"));
        model.addAttribute("rating1", ratingService.getRating("bejeweled", "durky"));
        model.addAttribute("rating2", ratingService.getRating("bejeweled", "tono"));
        model.addAttribute("rating3", ratingService.getRating("bejeweled", "peter"));
        model.addAttribute("AVGrating", ratingService.getAverageRating("bejeweled"));
        model.addAttribute("array", field.getArray());
        model.addAttribute("actualScore", field.getScore());
        model.addAttribute("command", new FormCommand());
    }

}
