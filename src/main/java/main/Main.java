package main;

import entity.Adventurer;
import entity.Map;
import entity.Mountain;
import entity.Position;
import entity.Treasure;
import entity.enums.Orientation;
import exception.TechnicalException;
import services.ActionService;
import services.GameService;

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
     * The Game service.
     */
    private static final GameService gameService = new GameService(actionService);

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {

        try {

            System.out.println("Action possible :");
            System.out.println("1 = Démo (Carte pré-défini du sprint 1)");
            System.out.println("2 = chargé carte et aventurier");
            System.out.println("E (Exit)");

            final Scanner keyboard = new Scanner(System.in);
            final String choice = keyboard.nextLine();

            if ("1".equals(choice)) {
                demo();
            } else if ("2".equals(choice)) {
                gameService.launchGame();
            } else if ("E".equals(choice)) {
                return;
            } else {
                System.out.println("\nSaisie incorrect\n");
            }
        } catch (final TechnicalException e) {
            System.out.printf("%n%s : %s%n", e.getCodeError(), e.getMessageError());
            throw e;
        }
    }

    /**
     * Demo.
     */
    private static void demo() {

        final Scanner keyboard = new Scanner(System.in);

        final ArrayList<Mountain> mountains = new ArrayList<>();
        mountains.add(new Mountain(3, 3));

        final ArrayList<Treasure> treasures = new ArrayList<>();
        treasures.add(new Treasure(1, 2, 2));

        final Map map = new Map(5, 5, mountains, treasures);
        final Adventurer adventurer = new Adventurer("Toto", new Position(1, 1), Orientation.E, null);

        while (true) {
            System.out.printf("Carte (colonne x ligne) : %sx%s Position aventurier (colonne - ligne - orientation) : %s - %s - %s Nombre de trésors : %s%n",
                    map.getColumnNumber(), map.getLineNumber(),
                    adventurer.getPosition().getColumn(), adventurer.getPosition().getLine(), adventurer.getOrientation(),
                    adventurer.getTreasureNumber());
            System.out.println("Action possible : A - D - G - E (Exit)");

            final String action = keyboard.nextLine();

            if ("E".equals(action)) {
                return;
            }

            actionService.executeAction(action, adventurer, map, true);
        }
    }
}
