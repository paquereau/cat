package services;

import entity.Adventurer;
import entity.Map;
import entity.Position;
import entity.Treasure;
import entity.enums.Orientation;
import exception.TechnicalException;

import java.util.List;

/**
 * The type Action service.
 */
public class ActionService {

    private static ActionService actionService;

    /**
     * Instantiates a new Action service.
     */
    private ActionService() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ActionService getInstance() {
        if (actionService == null) {
            actionService = new ActionService();
        }
        return actionService;
    }

    /**
     * Execute action boolean.
     *
     * @param action     the action
     * @param adventurer the adventurer
     * @param map        the map
     * @param isDemo     the is demo
     */
    public void executeAction(final String action, final Adventurer adventurer, final Map map, final boolean isDemo) {

        // Choose type of action (forward, right, left)
        switch (action) {
            case "A" -> moveAdventurer(adventurer, map, isDemo);
            case "D" -> moveRight(adventurer);
            case "G" -> moveLeft(adventurer);
            default -> System.out.println("\nIncorrect command\n");
        }

        waitSecond();
    }

    /**
     * Move adventurer.
     *
     * @param adventurer the adventurer
     * @param map        the map
     * @param isDemo     the is demo
     */
    private synchronized void moveAdventurer(final Adventurer adventurer, final Map map, final boolean isDemo) {

        final Position nextPosition = computeNextPosition(adventurer.getPosition(), adventurer.getOrientation());
        final List<Treasure> treasures = map.getTreasures();

        // Check if the move is authorized
        if (checkMove(nextPosition, map)) {
            map.getPositions().put(adventurer.getName(), nextPosition);
            adventurer.setPosition(nextPosition);
            adventurer.setTreasureNumber(computeTreasure(adventurer, treasures));
        } else if (isDemo) {
            System.out.println("\nIgnored action\n");
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
        return !map.getPositions().containsValue(nextPosition)
                && nextPosition.getLine() > 0 && nextPosition.getLine() <= map.getLineNumber()
                && nextPosition.getColumn() > 0 && nextPosition.getColumn() <= map.getColumnNumber()
                && !map.getMountains().contains(nextPosition);
    }

    /**
     * Compute treasure int.
     *
     * @param adventurer the adventurer
     * @param treasures  the treasures
     * @return the int
     */
    private int computeTreasure(final Adventurer adventurer, final List<Treasure> treasures) {

        final int index = treasures.indexOf(adventurer.getPosition());

        final int currentTreasureNumber = adventurer.getTreasureNumber();

        if (index == -1) {
            return currentTreasureNumber;
        }

        final Treasure treasure = treasures.get(index);
        treasures.remove(treasure);

        waitSecond();

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

    /**
     * Wait second.
     */
    private void waitSecond() {
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            throw new TechnicalException("ERREUR_THREAD", "Erreur lors de la mise en pause du thread", e);
        }
    }
}
