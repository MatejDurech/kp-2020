package sk.tuke.gamestudio.game.bejeweled.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.bejeweled.core.Field;
import sk.tuke.gamestudio.game.bejeweled.core.GameState;
import sk.tuke.gamestudio.game.bejeweled.core.Move;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.Scanner;

public class ConsoleUI {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    private static final String GAME_NAME = "bejeweled";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    //private ScoreService scoreService = new ScoreServiceJDBC();
    //private CommentService commentService = new CommentServiceJDBC();
    //private RatingService ratingService = new RatingServiceJDBC();
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[46m";

    private int[] array = new int[4];
    private Field field;
    private String answer;

    public ConsoleUI(Field field) {
        this.field = field;
    }
    public void entry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("First Tile");
        for (int i = 0; i < 4; i++) {
            if (i == 0) System.out.println("ROW:");
            if (i == 1) System.out.println("COLUMN");
            if (i == 2) {
                System.out.println("Second Tile:");
                System.out.println("ROW:");
            }
            if (i == 3) System.out.println("COLUMN");
            while (true)
                try {
                    array[i] = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter the correct value:");
                }

            while (array[i] > field.getSize() || array[i] < 1) {
                System.out.println("Please enter the correct value:");
                while (true)
                    try {
                        array[i] = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.println("Please enter the correct value:");
                    }
            }

        }
        if(array[0] == array[2] && ( array[1] - array[3] == -1 || array[1] - array[3] == 1) || array[1] == array[3] && ( array[0] - array[2] == -1 || array[0] - array[2] == 1) ){//podmienka na urcenie spravnej vstunej hodnoty od uzivatela
            System.out.println();
        }else{
            entry();
        }
    }

    public int[] getArray() {
        array[0] = array[0] - 1;
        array[1] = array[1] - 1;
        array[2] = array[2] - 1;
        array[3] = array[3] - 1;
        return array;
    }


    public void play() {
        printBejeweled();

        //top score
        topScore();

        //top comments
        topComments();

        topRating();

        averageRating();

        game();

        //write score
        score();

        //write comment
        comment();

        //write rating
        rating();


    }
    private void printBejeweled(){
        System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK +  " ---     ---       .    ---   .       .    ---   .       ---    ---  " + ANSI_RESET);
        System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK +  "|   >   |          |   |      |       |   |      |      |      |    |" + ANSI_RESET);
        System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK +  " ---     ---            ---                ---           ---         "  + ANSI_RESET);
        System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK +  "|   >   |      |   |   |      |   |   |   |      |      |      |    |" + ANSI_RESET);
        System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK +  " ---     ---    ---     ---    ---  ---    ---    ---    ---    ---  " + ANSI_RESET);
    }


    private void game(){
        while (field.getGameStateRepeat() == GameState.REPEAT) {
            int timeForLevel = 30000;
            int scoreForLevel = 500;
            field.currentTime();
            while (field.getGameState() == GameState.PLAYING) {
                field.generate();
                report();
                while (field.getGameTime() == GameState.TIME_OK) {
                    entry();
                    field.test();
                    Move move = new Move(field);
                    move.moveTile(getArray());
                    field.control();
                    report();
                    System.out.println(ANSI_RED + "SCORE:" + ANSI_RESET + field.getScore());
                    field.time(timeForLevel);
                }
                if (field.getScore() > scoreForLevel) {
                    System.out.println(ANSI_PURPLE + "NEXT LEVEL CONGRATULATION" + ANSI_RESET);
                    field.setGameState(GameState.PLAYING);
                    field.currentTime();
                    field.setLevel(1);
                } else {
                    field.setGameState(GameState.GAME_OVER);
                }
                timeForLevel = timeForLevel + 30000;
                scoreForLevel *= 3;
            }
            infoScore();
            questionForRepeat();
        }
    }
    private void infoScore() {
        System.out.println("TIME OUT");
        System.out.print(ANSI_RED + "YOUR SCORE IS : " + ANSI_RESET);
        System.out.println(ANSI_BLUE + field.getScore() + ANSI_RESET);
        System.out.print(ANSI_RED + "YOU DONE " + ANSI_BLUE + field.getLevel() + ANSI_RED + " LEVEL" + ANSI_RESET);
        System.out.println();
    }

    private void questionForRepeat(){
        System.out.println(ANSI_YELLOW + "Do you want play again? y/n : " + ANSI_RESET);
        String answer = new Scanner(System.in).nextLine();
        while((!answer.equals("y") && (!answer.equals("n")))){
            answer = new Scanner(System.in).nextLine();
        }
        if(answer.equals("y")){
            field.setGameStateRepeat(GameState.REPEAT);
            field.setGameState(GameState.PLAYING);
            field.setScore(0);
            field.setLevel(-field.getLevel());
        }
        if(answer.equals("n")) {
            field.setGameStateRepeat(GameState.DONT_REPEAT);
        }
    }

    private void comment() {
        System.out.println(ANSI_YELLOW + "Do you want comment this game? y/n : " + ANSI_RESET);
        String answer = new Scanner(System.in).nextLine();
        while((!answer.equals("y") && (!answer.equals("n")))){
            answer = new Scanner(System.in).nextLine();
        }
        if(answer.equals("y")){
            String comment = new Scanner(System.in).nextLine();
            while(comment.length() > 500){
                comment = new Scanner(System.in).nextLine();
            }try {
                commentService.addComment(new Comment(System.getProperty("user.name"), GAME_NAME, comment, new Date()));
            } catch (CommentException e) {
                e.printStackTrace();
            }
        }
    }
    private void score(){
        System.out.print(ANSI_YELLOW + "Please enter your name: " + ANSI_RESET);
        String name = new Scanner(System.in).nextLine();
        while(name.length() > 64){
            name = new Scanner(System.in).nextLine();
        }
        scoreService.addScore(new Score(name, GAME_NAME, field.getScore() , new Date()));
    }

    private void topScore(){
        System.out.println(ANSI_YELLOW + "Do you want to see top score? y/n : "  + ANSI_RESET);
        answer = new Scanner(System.in).nextLine();
        while((!answer.equals("y") && (!answer.equals("n")))){
            answer = new Scanner(System.in).nextLine();
        }
        if(answer.equals("y")){
            System.out.println(scoreService.getBestScores("bejeweled"));
        }
    }

    private void topComments(){
        System.out.println(ANSI_YELLOW + "Do you want to see comments? y/n: "  + ANSI_RESET);
        answer = new Scanner(System.in).nextLine();
        while((!answer.equals("y") && (!answer.equals("n")))){
            answer = new Scanner(System.in).nextLine();
        }
        if(answer.equals("y")){
            try {
                System.out.println(commentService.getComments("bejeweled"));
            } catch (CommentException e) {
                e.printStackTrace();
            }

        }
    }
    private void topRating(){
        System.out.println(ANSI_YELLOW + "Do you want to see rating? y/n: "  + ANSI_RESET);
        answer = new Scanner(System.in).nextLine();
        while((!answer.equals("y") && (!answer.equals("n")))){
            answer = new Scanner(System.in).nextLine();
        }
        if(answer.equals("y")){
            try {
                System.out.println("This is rating by user durky: " + ratingService.getRating("bejeweled", "durky"));
            } catch (RatingException e) {
                e.printStackTrace();
            }

        }
    }
    private void averageRating(){
        System.out.println(ANSI_YELLOW + "Do you want to see average rating? y/n: "  + ANSI_RESET);
        answer = new Scanner(System.in).nextLine();
        while((!answer.equals("y") && (!answer.equals("n")))){
            answer = new Scanner(System.in).nextLine();
        }
        if(answer.equals("y")){
            try {
                System.out.println("This is average rating this game: " + ratingService.getAverageRating("bejeweled"));
            } catch (RatingException e) {
                e.printStackTrace();
            }

        }
    }

    private void rating(){
        System.out.println(ANSI_YELLOW + "Do you want rate this game? y/n : " + ANSI_RESET);
        String answer = new Scanner(System.in).nextLine();
        while((!answer.equals("y") && (!answer.equals("n")))){
            answer = new Scanner(System.in).nextLine();
        }
        if(answer.equals("y")) {
            Scanner scanner = new Scanner(System.in);
            int rating = Integer.parseInt(scanner.nextLine());
            while(rating > 5 || rating < 1){
                rating = Integer.parseInt(scanner.nextLine());
            }try {
                ratingService.setRating(new Rating(System.getProperty("user.name"), GAME_NAME, rating, new Date()));
            } catch (RatingException e) {
                e.printStackTrace();
            }
        }
    }

    private void report() {
        for (int i = 0; i < field.getSize(); i++) {
            System.out.format(" %d ", i + 1);
        }
        System.out.println();


        for (int i = 0; i < field.getArray().length; i++) {
            for (int j = 0; j < field.getArray()[i].length; j++) {
                if (field.getArray()[j][i].equals("A")) {
                    System.out.print(ANSI_RED_BACKGROUND + "   " + ANSI_RESET);
                }
                if (field.getArray()[j][i].equals("B")) {
                    System.out.print(ANSI_BLUE_BACKGROUND + "   " + ANSI_RESET);
                }
                if (field.getArray()[j][i].equals("C")) {
                    System.out.print(ANSI_GREEN_BACKGROUND + "   " + ANSI_RESET);
                }
                if (field.getArray()[j][i].equals("D")) {
                    System.out.print(ANSI_PURPLE_BACKGROUND + "   " + ANSI_RESET);
                }
                if (field.getArray()[j][i].equals("E")) {
                    System.out.print(ANSI_YELLOW_BACKGROUND + "   " + ANSI_RESET);
                }
                if (field.getArray()[j][i].equals("F")) {
                    System.out.print(ANSI_CYAN_BACKGROUND + "   " + ANSI_RESET);
                }
            }
            System.out.println(i + 1);
        }
        System.out.println();
    }
}