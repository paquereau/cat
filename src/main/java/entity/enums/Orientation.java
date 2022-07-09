package entity.enums;

import entity.Position;

import java.util.stream.Stream;

/**
 * The enum Orientation.
 */
public enum Orientation {

    N(0, 0, -1),
    E(1, 1, 0),
    S(2, 0, 1),
    O(3, -1, 0);

    /**
     * The Order.
     */
    private int order;

    private Position positionToAdd;

    Orientation(int order, int column, int line) {
        this.order = order;
        this.positionToAdd = new Position(column, line);
    }

    public static Orientation getOrientationByOrder(int order) {
        return Stream.of(Orientation.values()).filter(orientation -> order == orientation.getOrder()).findFirst().orElseThrow(() -> new RuntimeException("Order not fund"));
    }

    public int getOrder() {
        return order;
    }

    public Position getPositionToAdd() {
        return positionToAdd;
    }
}
