package services;

import entity.Adventurer;
import entity.Map;
import entity.Position;
import entity.Treasure;
import entity.enums.Orientation;

import java.util.List;

/**
 * The type Action service.
 */
public class ActionService {

    /**
     * Execute action boolean.
     *
     * @param action     the action
     * @param adventurer the adventurer
     * @param map        the map
     * @param isDemo     the is demo
     */
    public void executeAction(final String action, final Adventurer adventurer, final Map map, final boolean isDemo) {

        switch (action) {
            case "A":
                moveAdventurer(adventurer, map, isDemo);
                break;
            case "D":
                moveRight(adventurer);
                break;
            case "G":
                moveLeft(adventurer);
                break;
            default:
                System.out.println("\nSaisie incorrect\n");
        }
    }

    /**
     * Move adventurer.
     *
     * @param adventurer the adventurer
     * @param map        the map
     * @param isDemo     the is demo
     */
    private void moveAdventurer(final Adventurer adventurer, final Map map, final boolean isDemo) {

        final Position nextPosition = computeNextPosition(adventurer.getPosition(), adventurer.getOrientation());
        final List<Treasure> treasures = map.getTreasures();

        if (checkMove(nextPosition, map)) {
            adventurer.setPosition(nextPosition);
            adventurer.setTreasureNumber(computeTreasure(adventurer.getPosition(), adventurer.getTreasureNumber(), treasures));
        } else if (isDemo) {
            System.out.println("\nAction ignoré\n");
        }
    }

    /**
     * Compute next position position.
     *
     * @param position    the position
     * @param orientation the orientation
     * @return the position
     */
    private Position computeNextPosition(final Position position, final Orientation orientation) {
        return position.add(orientation.getPositionToAdd());
    }

    /**
     * Check move boolean.
     *
     * @param nextPosition the next position
     * @param map          the map
     * @return the boolean
     */
    private boolean checkMove(final Position nextPosition, final Map map) {
        return nextPosition.getLine() > 0 && nextPosition.getLine() <= map.getLineNumber()
                && nextPosition.getColumn() > 0 && nextPosition.getColumn() <= map.getColumnNumber()
                && !map.getMountains().contains(nextPosition);
    }

    /**
     * Compute treasure int.
     *
     * @param position              the position
     * @param currentTreasureNumber the current treasure number
     * @param treasures             the treasures
     * @return the int
     */
    private int computeTreasure(final Position position, final int currentTreasureNumber, final List<Treasure> treasures) {

        final int index = treasures.indexOf(position);

        if (index == -1) {
            return currentTreasureNumber;
        }

        final Treasure treasure = treasures.get(index);
        treasures.remove(treasure);

        return currentTreasureNumber + treasure.getTreasureNumber();
    }

    /**
     * Move left.
     *
     * @param adventurer the adventurer
     */
    private void moveLeft(final Adventurer adventurer) {
        final int nextOrder = Math.floorMod(adventurer.getOrientation().getOrder() - 1, 4);
        adventurer.setOrientation(Orientation.getOrientationByOrder(nextOrder));
    }

    /**
     * Move right.
     *
     * @param adventurer the adventurer
     */
    private void moveRight(final Adventurer adventurer) {
        final int nextOrder = (adventurer.getOrientation().getOrder() + 1) % 4;
        adventurer.setOrientation(Orientation.getOrientationByOrder(nextOrder));
    }
}
