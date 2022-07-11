package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
     * The Positions.
     */
    private ConcurrentMap<String, Position> positions;

    /**
     * Instantiates a new Map.
     */
    public Map() {
        mountains = new ArrayList<>();
        treasures = new ArrayList<>();
        positions = new ConcurrentHashMap<>();
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
        positions = new ConcurrentHashMap<>();
    }

    /**
     * Gets column number.
     *
     * @return the column number
     */
    public Integer getColumnNumber() {
        return columnNumber;
    }

    /**
     * Sets column number.
     *
     * @param columnNumber the column number
     */
    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * Gets line number.
     *
     * @return the line number
     */
    public Integer getLineNumber() {
        return lineNumber;
    }

    /**
     * Sets line number.
     *
     * @param lineNumber the line number
     */
    public void setLineNumber(Integer lineNumber) {
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
     * Gets positions.
     *
     * @return the positions
     */
    public ConcurrentMap<String, Position> getPositions() {
        return positions;
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
