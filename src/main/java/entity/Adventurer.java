package entity;

import entity.enums.Orientation;

import java.util.List;

/**
 * The type Adventurer.
 */
public class Adventurer {

    /**
     * The Name.
     */
    private String name;

    /**
     * The Position.
     */
    private Position position;

    /**
     * The Treasure number.
     */
    private int treasureNumber;

    /**
     * The Orientation.
     */
    private Orientation orientation;

    /**
     * The Action.
     */
    private List<String> actions;

    /**
     * Instantiates a new Adventurer.
     *
     * @param name        the name
     * @param column      the column
     * @param line        the line
     * @param orientation the orientation
     */
    public Adventurer(String name, int column, int line, Orientation orientation, List<String> actions) {
        this.name = name;
        this.position = new Position(column, line);
        this.treasureNumber = 0;
        this.orientation = orientation;
        this.actions = actions;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets treasure number.
     *
     * @return the treasure number
     */
    public int getTreasureNumber() {
        return treasureNumber;
    }

    /**
     * Sets treasure number.
     *
     * @param treasureNumber the treasure number
     */
    public void setTreasureNumber(int treasureNumber) {
        this.treasureNumber = treasureNumber;
    }

    /**
     * Gets orientation.
     *
     * @return the orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Sets orientation.
     *
     * @param orientation the orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Gets actions.
     *
     * @return the actions
     */
    public List<String> getActions() {
        return actions;
    }

    /**
     * Sets actions.
     *
     * @param actions the actions
     */
    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return String.format("%s %s-%s %s %s", name, position.getColumn(), position.getLine(), orientation.name(), treasureNumber);
    }
}
