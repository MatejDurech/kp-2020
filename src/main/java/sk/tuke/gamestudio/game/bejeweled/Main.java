package sk.tuke.gamestudio.game.bejeweled;


import sk.tuke.gamestudio.game.bejeweled.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.bejeweled.core.Field;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(5);
       ConsoleUI consoleUi = new ConsoleUI(field);
       consoleUi.play();

    }
}