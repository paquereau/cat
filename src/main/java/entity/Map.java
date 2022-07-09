package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Map.
 */
public class Map {

    /**
     * The Column number.
     */
    private Integer columnNumber;

    /**
     * The Line number.
     */
    private Integer lineNumber;

    /**
     * The Mountains.
     */
    private List<Mountain> mountains;

    /**
     * The Treasures.
     */
    private List<Treasure> treasures;

    /**
     * Instantiates a new Map.
     */
    public Map() {
        mountains = new ArrayList<>();
        treasures = new ArrayList<>();
    }

    /**
     * Instantiates a new Map.
     *
     * @param columnNumber the column number
     * @param lineNumber   the line number
     * @param mountains    the mountains
     * @param treasures    the treasures
     */
    public Map(Integer columnNumber, Integer lineNumber, List<Mountain> mountains, List<Treasure> treasures) {
        this.columnNumber = columnNumber;
        this.lineNumber = lineNumber;
        this.mountains = mountains;
        this.treasures = treasures;
    }

    /**
     * Gets column number.
     *
     * @return the column number
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * Sets column number.
     *
     * @param columnNumber the column number
     */
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * Gets line number.
     *
     * @return the line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Sets line number.
     *
     * @param lineNumber the line number
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Gets mountains.
     *
     * @return the mountains
     */
    public List<Mountain> getMountains() {
        return mountains;
    }

    /**
     * Sets mountains.
     *
     * @param mountains the mountains
     */
    public void setMountains(List<Mountain> mountains) {
        this.mountains = mountains;
    }

    /**
     * Gets treasures.
     *
     * @return the treasures
     */
    public List<Treasure> getTreasures() {
        return treasures;
    }

    /**
     * Sets treasures.
     *
     * @param treasures the treasures
     */
    public void setTreasures(List<Treasure> treasures) {
        this.treasures = treasures;
    }

    /**
     * Is valid boolean.
     *
     * @return the boolean
     */
    public boolean isValid() {
        return lineNumber != null && columnNumber != null;
    }
}
