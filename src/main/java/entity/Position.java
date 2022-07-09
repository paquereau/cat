package entity;

/**
 * The type Position.
 */
public class Position {

    /**
     * The Column.
     */
    private int column;

    /**
     * The Line.
     */
    private int line;

    /**
     * Instantiates a new Position.
     *
     * @param column the column
     * @param line   the line
     */
    public Position(final int column, final int line) {
        this.column = column;
        this.line = line;
    }

    /**
     * Gets column.
     *
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets column.
     *
     * @param column the column
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Add position.
     *
     * @param positionToAdd the position to add
     * @return the position
     */
    public Position add(Position positionToAdd) {
        return new Position(column + positionToAdd.column, line + positionToAdd.line);
    }

    /**
     * Gets line.
     *
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * Sets line.
     *
     * @param line the line
     */
    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public boolean equals(Object object) {

        if (object == this) {
            return true;
        }

        if (!(object instanceof Position)) {
            return false;
        }

        final Position position = (Position) object;

        return line == position.line && column == position.column;
    }
}
