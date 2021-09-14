package sk.tuke.gamestudio.game.bejeweled.core;


public class Move {

    private Field field;

    public Move(Field field) {
        this.field = field;
    }

    public void moveTile(int[] array) {
        field.setArray(field.getArray(),array[0], array[1], array[2], array[3]);
    }

}
