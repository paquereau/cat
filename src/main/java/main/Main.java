package main;

import entity.Adventurer;
import entity.Map;
import entity.Mountain;
import entity.Treasure;
import entity.enums.Orientation;
import services.ActionService;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The type Main.
 */
public class Main {

    /**
     * The Action service.
     */
    private static final ActionService actionService = new ActionService();

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {

        final Scanner keyboard = new Scanner(System.in);

        final ArrayList<Mountain> mountains = new ArrayList<>();
        mountains.add(new Mountain(3, 3));

        final ArrayList<Treasure> treasures = new ArrayList<>();
        treasures.add(new Treasure(1, 2, 2));

        final Map map = new Map(5, 5, mountains, treasures);
        final Adventurer adventurer = new Adventurer("Toto", 1, 1, Orientation.E, null);

        while (true) {
            System.out.println(
                    String.format("Carte (ligne x colonne) : %sx%s Position aventurier (ligne - colonne - orientation) : %s - %s - %s Nombre de trésors : %s",
                            map.getLineNumber(), map.getColumnNumber(),
                            adventurer.getPosition().getLine(), adventurer.getPosition().getColumn(), adventurer.getOrientation(),
                            adventurer.getTreasureNumber()));
            System.out.println("Action possible : A - D - G - E (Exit)");

            String action = keyboard.nextLine();

            if ("E".equals(action)) {
                return;
            }

            actionService.executeAction(action, adventurer, map);
        }
    }
}
