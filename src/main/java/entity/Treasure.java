package entity;

/**
 * The type Treasure.
 */
public class Treasure extends Position {

    /**
     * The Treasure number.
     */
    private int treasureNumber;

    /**
     * Instantiates a new Position.
     *
     * @param column         the column
     * @param line           the line
     * @param treasureNumber the treasure number
     */
    public Treasure(int column, int line, int treasureNumber) {
        super(column, line);
        this.treasureNumber = treasureNumber;
    }

    /**
     * Gets treasure number.
     *
     * @return the treasure number
     */
    public int getTreasureNumber() {
        return treasureNumber;
    }
}
