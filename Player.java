public class Player {

    private String name;
    private int moveCount;
    private boolean won;
    private boolean myTurn;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public void printWinnerAnnouncement() {
        System.out.println(getName() + " wins in " + getMoveCount() + " moves!");
    }
}
