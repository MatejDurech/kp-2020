package sk.tuke.gamestudio.game.bejeweled.core;

public class Field {
    private String[][] array;
    private String[][] arrayForControlTiles;
    private int size;
    private int score = 0;
    private int level = 0;
    private long time;
    private String[] arr = {"A", "B", "C", "D", "E", "F"};
    private String[][] arrayTest;
    private String helpRandomValue = arr[(int) (Math.random() * (5 + 1))];
    private MoveState moveState;
    private GameState gameStateRepeat = GameState.REPEAT;
    private GameState gameState = GameState.PLAYING;
    private GameState gameTime = GameState.TIME_OK;


    public Field(int size) {
        this.size = size;
        this.array = new String[size][size];
        this.arrayForControlTiles = new String[size][size];
        arrayTest = new String[getSize() + 6][getSize() + 6];
        moveState = MoveState.IS_NOT_MOVE_POSSIBLE;
    }

    public int getSize() {
        return size;
    }

    public GameState getGameStateRepeat() {
        return gameStateRepeat;
    }

    public GameState getGameTime() {
        return gameTime;
    }

    public void setGameStateRepeat(GameState gameStateRepeat) {
        this.gameStateRepeat = gameStateRepeat;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getLevel() {
        return level;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int level) {
        this.level = this.level + level;
    }

    public String[][] getArray() {
        return array;
    }

    public int getScore() {
        return score;
    }


    void setArray(String[][] array, int row_1, int column_1, int row_2, int column_2) {
        this.array = array;
        String help = array[column_1][row_1];
        array[column_1][row_1] = array[column_2][row_2];
        array[column_2][row_2] = help;
        explode();
        if (moveState == MoveState.IS_NOT_MOVE_POSSIBLE) {
            help = array[column_1][row_1];
            array[column_1][row_1] = array[column_2][row_2];
            array[column_2][row_2] = help;
        }
    }

    public void generate() {

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                helpRandomValue = arr[(int) (Math.random() * (5 + 1))];
                this.array[j][i] = helpRandomValue;
            }
        }
        while (helperGenerate()) ;
    }

    private boolean helperGenerate() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length - 2; j++) {
                helpRandomValue = arr[(int) (Math.random() * (5 + 1))];
                if (array[j][i].equals(array[j + 1][i]) && array[j][i].equals(array[j + 2][i]) && array[j + 2][i].equals(array[j + 1][i])) {
                    array[j][i] = helpRandomValue;
                    return true;
                }
            }
        }
        for (int i = 0; i < array.length - 2; i++) {
            for (int j = 0; j < array[i].length; j++) {
                helpRandomValue = arr[(int) (Math.random() * (5 + 1))];
                if (array[j][i].equals(array[j][i + 1]) && array[j][i].equals(array[j][i + 2]) && array[j][i + 2].equals(array[j][i + 1])) {
                    array[j][i] = helpRandomValue;
                    return true;
                }
            }
        }
        return false;
    }


    private boolean testIsTilesPossible() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                arrayTest[j + 3][i + 3] = array[j][i];
            }
        }
        for (int i = 0; i < arrayTest.length; i++) {
            for (int j = 0; j < arrayTest[i].length; j++) {
                if (arrayTest[j][i] == null) {
                    arrayTest[j][i] = "N";
                }
            }
        }
        for (int i = 3; i < arrayTest.length - 3; i++) {
            for (int j = 3; j < arrayTest.length - 3; j++) {
                if (arrayTest[j][i].equals(arrayTest[j + 1][i]) && arrayTest[j][i].equals(arrayTest[j - 1][i - 1])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j + 1][i]) && arrayTest[j][i].equals(arrayTest[j - 2][i])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j + 1][i]) && arrayTest[j][i].equals(arrayTest[j - 1][i + 1])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j + 1][i]) && arrayTest[j][i].equals(arrayTest[j + 2][i - 1])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j + 1][i]) && arrayTest[j][i].equals(arrayTest[j + 3][i])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j + 1][i]) && arrayTest[j][i].equals(arrayTest[j + 2][i + 1])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j][i + 1]) && arrayTest[j][i].equals(arrayTest[j - 1][i - 1])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j][i + 1]) && arrayTest[j][i].equals(arrayTest[j][i - 2])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j][i + 1]) && arrayTest[j][i].equals(arrayTest[j + 1][i - 1])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j][i + 1]) && arrayTest[j][i].equals(arrayTest[j - 1][i + 2])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j][i + 1]) && arrayTest[j][i].equals(arrayTest[j][i + 3])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j][i + 1]) && arrayTest[j][i].equals(arrayTest[j + 1][i + 2])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j + 2][i]) && arrayTest[j][i].equals(arrayTest[j + 1][i - 1])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j + 2][i]) && arrayTest[j][i].equals(arrayTest[j + 1][i + 1])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j][i + 2]) && arrayTest[j][i].equals(arrayTest[j - 1][i + 1])) {
                    return true;
                }
                if (arrayTest[j][i].equals(arrayTest[j][i + 2]) && arrayTest[j][i].equals(arrayTest[j + 1][i + 1])) {
                    return true;
                }
            }
        }
        return false;
    }

    private void mixer() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                int random_number = (int) (Math.random() * (getSize()));
                String help = array[j][i];
                array[j][i] = array[random_number][random_number];
                array[random_number][random_number] = help;
            }
        }
        while (helperGenerate()) ;
    }

    private void explode() {
        moveState = MoveState.IS_NOT_MOVE_POSSIBLE;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length - 2; j++) {
                if (array[j][i].equals(array[j + 1][i]) && array[j][i].equals(array[j + 2][i]) && array[j + 2][i].equals(array[j + 1][i])) {
                    arrayForControlTiles[j][i] = array[j][i];
                    arrayForControlTiles[j + 1][i] = array[j][i];
                    arrayForControlTiles[j + 2][i] = array[j][i];
                    moveState = MoveState.IS_MOVE_POSSIBLE;
                    score += 90;
                }
            }
        }
        for (int i = 0; i < array.length - 2; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[j][i].equals(array[j][i + 1]) && array[j][i].equals(array[j][i + 2]) && array[j][i + 2].equals(array[j][i + 1])) {
                    arrayForControlTiles[j][i] = array[j][i];
                    arrayForControlTiles[j][i + 1] = array[j][i];
                    arrayForControlTiles[j][i + 2] = array[j][i];
                    moveState = MoveState.IS_MOVE_POSSIBLE;
                    score += 90;
                }
            }
        }
        for (int i = 0; i < arrayForControlTiles.length; i++) {
            for (int j = 0; j < arrayForControlTiles[i].length; j++) {
                if (arrayForControlTiles[j][i] == null) arrayForControlTiles[j][i] = "N";
                if (!arrayForControlTiles[j][i].equals("N")) {
                    array[j][i] = " ";
                }
            }
        }
        arrayForControlTiles = new String[size][size];
        gravitation();
        generateValueForEmptyTiles();

    }

    private void gravitation() {
        for (int i = array.length - 2; i >= 0; i--) {
            for (int j = 0; j < array[i].length; j++) {
                int help_i = i;
                while (array[j][help_i + 1].equals(" ") && array.length - 2 > help_i) {
                    array[j][help_i + 1] = array[j][help_i];
                    array[j][help_i] = " ";
                    help_i++;
                }
                if (array[j][help_i + 1].equals(" ")) {
                    array[j][help_i + 1] = array[j][help_i];
                    array[j][help_i] = " ";
                }
            }
        }
    }

    private void generateValueForEmptyTiles() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[j][i].equals(" ")) {
                    helpRandomValue = arr[(int) (Math.random() * (5 + 1))];
                    this.array[j][i] = helpRandomValue;
                }
            }
        }
    }

    private boolean scan() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length - 2; j++) {
                helpRandomValue = arr[(int) (Math.random() * (5 + 1))];
                if (array[j][i].equals(array[j + 1][i]) && array[j][i].equals(array[j + 2][i]) && array[j + 2][i].equals(array[j + 1][i])) {
                    return true;
                }
            }
        }
        for (int i = 0; i < array.length - 2; i++) {
            for (int j = 0; j < array[i].length; j++) {
                helpRandomValue = arr[(int) (Math.random() * (5 + 1))];
                if (array[j][i].equals(array[j][i + 1]) && array[j][i].equals(array[j][i + 2]) && array[j][i + 2].equals(array[j][i + 1])) {
                    return true;
                }
            }
        }
        return false;
    }


    public void test() {
        int counter = 0;
        while (!testIsTilesPossible()) {
            mixer();//mix field
            if (counter == 30) {
                generate();
                counter = 0;
            }
            counter++;
        }
    }


    public void control() {
        while (scan()) {
            explode();
        }
    }

    public void currentTime(){
        time = System.currentTimeMillis();
        gameTime = GameState.TIME_OK;
    }

    public void time(int times) {
        if (System.currentTimeMillis() - time < times) {
            gameTime = GameState.TIME_OK;
        } else {
            gameTime = GameState.TIME_OUT;
        }
    }
}