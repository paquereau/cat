package main;

import entity.Adventurer;
import entity.Map;
import entity.Mountain;
import entity.Position;
import entity.Treasure;
import entity.enums.Orientation;
import exception.BusinessException;
import exception.TechnicalException;
import parser.AdventurerParser;
import parser.MapParser;
import services.ActionService;
import services.GameService;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The type Main.
 */
public class Main {

    /**
     * The Map parser.
     */
    private static final MapParser mapParser = new MapParser();

    /**
     * The Adventurer parser.
     */
    private static final AdventurerParser adventurerParser = new AdventurerParser();

    /**
     * The Action service.
     */
    private static final ActionService actionService = new ActionService();

    /**
     * The Keyboard.
     */
    private static final Scanner keyboard = new Scanner(System.in);

    /**
     * The Game service.
     */
    private static final GameService gameService = new GameService(mapParser, adventurerParser, actionService, keyboard);

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

            final String choice = keyboard.nextLine();

            if (!"E".equals(choice)) {
                switch (choice) {
                    case "1" -> demo();
                    case "2" -> playWithFile();
                    default -> System.err.println("\nSaisie incorrect\n");
                }
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

    /**
     * Play with file.
     */
    private static void playWithFile() {
        final String out;
        try {
            out = gameService.launchGame();
            System.out.printf("Le résultat est disponible ici : %s%n", out);
        } catch (final BusinessException e) {
            System.out.printf("%n%s : %s%n", e.getCodeError(), e.getMessageError());
            e.printStackTrace();
        }
    }
}
