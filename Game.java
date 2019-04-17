public class Game {

    private String[][] board;
    private int dimension;
    private int winningCondition;
    private Player player1 = new Player();
    private Player player2 = new Player();
    private boolean gameOver;


    public Game() {

        dimension = Reader.getInputInt("Please enter dimension of board: ");

        while (true) {
            if (dimension < 3) {
                System.out.println("Dimension must be at last 3!");
                dimension = Reader.getInputInt("Please enter dimension of board: ");
            } else {
                break;
            }
        }

        winningCondition = Reader.getInputInt("Please enter the winning condition: ");

        while (true) {
            if (winningCondition < 3 || winningCondition > dimension) {
                System.out.println("Winning condition can't be less than 3 or bigger than dimension!");
                winningCondition = Reader.getInputInt("Please enter the winning condition: ");
            } else {
                break;
            }
        }

        board = new String[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = "";
            }
        }
    }

    //request player info
    public void play() {

        String move;
        String name;

        int row;
        int col;

        //first - request names for players
        player1.setName(Reader.getInput("Please enter name for the first player: "));
        player2.setName(Reader.getInput("Please enter name for the second player: "));

        //print the board
        printTheBoard();

        //player 1 allways is one who start the game
        player1.setMyTurn(true);

        //requesting inputs while game isn't over
        while (!gameOver) {

            //set letter depending on turn
            if (player1.isMyTurn()) {
                move = "X";
                name = player1.getName();
            } else {
                move = "O";
                name = player2.getName();
            }

            //request row and column for move
            row = Reader.getInputInt(name + ", please enter row for " + move + ": ") - 1;
            col = Reader.getInputInt(name + ", please enter column for " + move + ": ") - 1;

            //check if row and col are on table
            while (!isOnTable(row, col)) {
                System.out.println("row or column can't be bigger than dimension of board!");
                row = Reader.getInputInt(name + ", please enter row for " + move + ": ") - 1;
                col = Reader.getInputInt(name + ", please enter column for " + move + ": ") - 1;
            }

            //check if row and col cell is free
            while (!cellIsFree(row, col)) {
                System.out.println("this cell is alredy taken, please choose another cell!");
                row = Reader.getInputInt(name + ", please enter row for " + move + ": ") - 1;
                col = Reader.getInputInt(name + ", please enter column for " + move + ": ") - 1;
            }

            makeMove(move, row, col);

            //check for winner after every move
            //if there is no winner, change turn for players
            //else print winning announcement and quit method
            String winner = checkWinner(move, col);
            if (winner.equals("still no winner")){
                changeTurn(player1, player2);
            } else if (winner.equals(player1.getName())){
                player1.printWinnerAnnouncement();
                return;
            } else{
                player2.printWinnerAnnouncement();
                return;
            }

        }

    }

    public void makeMove(String move, int row, int col) {

        board[row][col] = move;

        //increase moves for player
        if (move.equals("X")) {
            player1.setMoveCount(player1.getMoveCount() + 1);
        } else {
            player2.setMoveCount(player2.getMoveCount() + 1);
        }

        printTheBoard();
    }

    //method to print rows
    private void printRow() {
        System.out.print(" +");
        for (int i = 0; i < dimension; i++) {
            System.out.print("---+");
        }
        System.out.println();
    }


    //method to print the Board
    public void printTheBoard() {

        //print column numbers
        int col = 1;
        System.out.print("   ");
        for (int i = 0; i < dimension; i++) {
            System.out.print(col++ + "   ");
        }
        System.out.println();

        //print first row
        printRow();

        for (int i = 0; i < dimension; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < dimension; j++) {
                if (board[i][j].equals("")) {
                    System.out.print("   |");
                } else {
                    System.out.print(" " + board[i][j] + " |");
                }
            }
            System.out.println();
            printRow();
        }

    }

    private boolean isOnTable(int row, int column) {
        if (row >= dimension || column >= dimension) {
            return false;
        } else {
            return true;
        }
    }

    private boolean cellIsFree(int row, int column) {
        if (board[row][column].equals("X") || board[row][column].equals("O")) {
            return false;
        } else {
            return true;
        }
    }

    public void changeTurn(Player player1, Player player2) {
        if (player1.isMyTurn() == true && player2.isMyTurn() == false) {
            player1.setMyTurn(false);
            player2.setMyTurn(true);
        } else if (player1.isMyTurn() == false && player2.isMyTurn() == true) {
            player1.setMyTurn(true);
            player2.setMyTurn(false);
        }
    }

    private String checkWinner(String move, int col) {

        String name;
        boolean someOneWins = false;


        if (move.equals("X")) {
            name = player1.getName();
        } else {
            name = player2.getName();
        }


        //horizontal================================================
        for (int i = 0; i < dimension - winningCondition + 1; i++) {
            for (int j = 0; j < dimension - winningCondition + 1; j++) {
                if (board[i][j].equals(move)) {
                    for (int k = 0; k < winningCondition; k++) {
                        if (!board[i][j].equals(board[i + k][j])) {
                            someOneWins = false;
                            break;
                        } else {
                            someOneWins = true;
                        }
                    }
                    if (someOneWins) {
                        break;
                    }
                }
            }
            if (someOneWins) {
                return name;
            }
        }


        //vertical==================================================
        for (int i = 0; i < dimension - winningCondition + 1; i++) {
            for (int j = 0; j < dimension - winningCondition + 1; j++) {
                if (board[i][j].equals(move)) {
                    for (int k = 0; k < winningCondition; k++) {
                        if (!board[i][j].equals(board[i][j + k])) {
                            someOneWins = false;
                            break;
                        } else {
                            someOneWins = true;
                        }
                    }
                    if (someOneWins) {
                        break;
                    }
                }
            }
            if (someOneWins) {
                return name;
            }
        }

        //check from left to right diagonal======================================
        for (int i = 0; i < dimension - winningCondition + 1; i++) {
            for (int j = 0; j < dimension - winningCondition + 1; j++) {
                if (board[i][j].equals(move)) {
                    for (int k = 0; k < winningCondition; k++) {
                        if (!board[i][j].equals(board[i + k][j + k])) {
                            someOneWins = false;
                            break;
                        } else {
                            someOneWins = true;
                        }
                    }
                    if (someOneWins) {
                        break;
                    }
                }
            }
            if (someOneWins) {
                return name;
            }
        }

        //check from right to left diagonal======================================

        for (int i = 0; i < dimension - winningCondition + 1; i++) {
            for (int j = winningCondition - 1; j < dimension; j++) {
                if (board[i][j].equals(move)) {
                    for (int k = 0; k < winningCondition; k++) {
                        if (!board[i][j].equals(board[i + k][j - k])) {
                            someOneWins = false;
                            break;
                        } else {
                            someOneWins = true;
                        }
                    }
                    if (someOneWins) {
                        break;
                    }
                }
            }
            if (someOneWins) {
                return name;
            }
        }

        //depending on column value decide in which part check for diagonal winning
        //to left or to right
        //also depend on our dimension - is it odd or even number
//        if (dimension % 2 == 0){
//            if (col < dimension / 2){
//                return checkDiagonalToRight(name, move, someOneWins);
//            }else{
//                return checkDiagonalToLeft(name, move, someOneWins);
//            }
//        }else{
//            if (col < dimension / 2){
//                return checkDiagonalToRight(name, move, someOneWins);
//            } else if (col > dimension / 2 + 1){
//                return checkDiagonalToLeft(name, move, someOneWins);
//            } else{
//                //check both
//                String str = checkDiagonalToLeft(name, move, someOneWins);
//                if (str.equals("still no winner")){
//                    return checkDiagonalToRight(name, move, someOneWins);
//                }else{
//                    return  str;
//                }
//            }
//        }
        return "still no winner";
    }

    private String checkDiagonalToRight(String name, String move, boolean someOneWins){

        someOneWins = false;
        for (int i = 0; i < dimension - winningCondition + 1; i++) {
            for (int j = 0; j < dimension - winningCondition + 1; j++) {
                if (board[i][j].equals(move)) {
                    for (int k = 0; k < winningCondition; k++) {
                        if (!board[i][j].equals(board[i + k][j + k])) {
                            someOneWins = false;
                            break;
                        } else {
                            someOneWins = true;
                        }
                    }
                    if (someOneWins) {
                        break;
                    }
                }
            }
            if (someOneWins) {
                return name;
            }
        }

        return "still no winner";
    }
    private String checkDiagonalToLeft(String name, String move, boolean someOneWins){

        someOneWins = false;
        for (int i = 0; i < dimension - winningCondition + 1; i++) {
            for (int j = winningCondition - 1; j < dimension; j++) {
                if (board[i][j].equals(move)) {
                    for (int k = 0; k < winningCondition; k++) {
                        if (!board[i][j].equals(board[i + k][j - k])) {
                            someOneWins = false;
                            break;
                        } else {
                            someOneWins = true;
                        }
                    }
                    if (someOneWins) {
                        break;
                    }
                }
            }
            if (someOneWins) {
                return name;
            }
        }
        return "still no winner";
    }

}
