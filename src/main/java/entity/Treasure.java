package entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        Treasure treasure = (Treasure) o;

        return treasureNumber == treasure.treasureNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), treasureNumber);
    }
}
