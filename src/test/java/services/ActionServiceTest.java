package services;

import entity.Adventurer;
import entity.Map;
import entity.Mountain;
import entity.Position;
import entity.Treasure;
import entity.enums.Orientation;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Action service test.
 */
public class ActionServiceTest {

    private final ActionService actionService = ActionService.getInstance();

    /**
     * Execute action test move adventurer.
     */
    @Test
    public void executeActionTestMoveAdventurer() {

        // Given
        final String action = "A";
        final Adventurer adventurer = createAdventurer(Orientation.E);
        final Map map = createMap();

        // When
        actionService.executeAction(action, adventurer, map, false);

        // Then
        // Check adventurer
        Assert.assertEquals(4, adventurer.getPosition().getColumn());
        Assert.assertEquals(3, adventurer.getPosition().getLine());
        Assert.assertEquals(0, adventurer.getTreasureNumber());
        Assert.assertEquals(Orientation.E, adventurer.getOrientation());

        // Check state map
        Assert.assertEquals(1, map.getTreasures().size());
    }

    /**
     * Execute action test move adventurer on treasure.
     */
    @Test
    public void executeActionTestMoveAdventurerOnTreasure() {

        // Given
        final String action = "A";
        final Adventurer adventurer = createAdventurer(Orientation.N);
        final Map map = createMap();

        // When
        actionService.executeAction(action, adventurer, map, false);

        // Then
        // Check state adventurer
        Assert.assertEquals(3, adventurer.getPosition().getColumn());
        Assert.assertEquals(2, adventurer.getPosition().getLine());
        Assert.assertEquals(2, adventurer.getTreasureNumber());
        Assert.assertEquals(Orientation.N, adventurer.getOrientation());

        // Check state map
        Assert.assertEquals(0, map.getTreasures().size());
    }

    /**
     * Execute action test move adventurer on mountain.
     */
    @Test
    public void executeActionTestMoveAdventurerOnMountain() {

        // Given
        final String action = "A";
        final Adventurer adventurer = createAdventurer(Orientation.S);
        final Map map = createMap();

        // When
        actionService.executeAction(action, adventurer, map, true);

        // Then
        // Check state adventurer
        Assert.assertEquals(3, adventurer.getPosition().getColumn());
        Assert.assertEquals(3, adventurer.getPosition().getLine());
        Assert.assertEquals(0, adventurer.getTreasureNumber());
        Assert.assertEquals(Orientation.S, adventurer.getOrientation());

        // Check state map
        Assert.assertEquals(1, map.getTreasures().size());
    }

    /**
     * Execute action test turn adventurer left.
     */
    @Test
    public void executeActionTestTurnAdventurerLeft() {

        // Given
        final String action = "G";
        final Adventurer adventurer = createAdventurer(Orientation.E);
        final Map map = createMap();

        // When
        actionService.executeAction(action, adventurer, map, true);

        // Then
        // Check state adventurer
        Assert.assertEquals(3, adventurer.getPosition().getColumn());
        Assert.assertEquals(3, adventurer.getPosition().getLine());
        Assert.assertEquals(0, adventurer.getTreasureNumber());
        Assert.assertEquals(Orientation.N, adventurer.getOrientation());

        // Check state map
        Assert.assertEquals(1, map.getTreasures().size());
    }

    /**
     * Execute action test turn adventurer right.
     */
    @Test
    public void executeActionTestTurnAdventurerRight() {

        // Given
        final String action = "D";
        final Adventurer adventurer = createAdventurer(Orientation.E);
        final Map map = createMap();

        // When
        actionService.executeAction(action, adventurer, map, true);

        // Then
        // Check state adventurer
        Assert.assertEquals(3, adventurer.getPosition().getColumn());
        Assert.assertEquals(3, adventurer.getPosition().getLine());
        Assert.assertEquals(0, adventurer.getTreasureNumber());
        Assert.assertEquals(Orientation.S, adventurer.getOrientation());

        // Check state map
        Assert.assertEquals(1, map.getTreasures().size());
    }

    @Test
    public void executeActionTestTurnAdventurerUnknownAction() {

        // Given
        final String action = "T";
        final Adventurer adventurer = createAdventurer(Orientation.E);
        final Map map = createMap();

        // When
        actionService.executeAction(action, adventurer, map, true);

        // Then
        // Check state adventurer
        Assert.assertEquals(3, adventurer.getPosition().getColumn());
        Assert.assertEquals(3, adventurer.getPosition().getLine());
        Assert.assertEquals(0, adventurer.getTreasureNumber());
        Assert.assertEquals(Orientation.E, adventurer.getOrientation());

        // Check state map
        Assert.assertEquals(1, map.getTreasures().size());
    }

    /**
     * Create aventurer adventurer.
     *
     * @param orientation the orientation
     * @return the adventurer
     */
    private Adventurer createAdventurer(final Orientation orientation) {
        return new Adventurer("Toto", new Position(3, 3), orientation, null);
    }

    /**
     * Create map map.
     *
     * @return the map
     */
    private Map createMap() {

        final List<Mountain> mountains = new ArrayList<>();
        mountains.add(new Mountain(3, 4));

        final List<Treasure> treasures = new ArrayList<>();
        treasures.add(new Treasure(3, 2, 2));

        return new Map(5, 5, mountains, treasures);
    }
}

