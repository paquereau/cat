package main;

import entity.Adventurer;
import entity.Map;
import entity.Mountain;
import entity.Treasure;
import entity.enums.Orientation;
import parser.AdventurerParser;
import parser.MapParser;
import services.ActionService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The type Main.
 */
public class Main {

    /**
     * The Action service.
     */
    private static final ActionService actionService = new ActionService();

    /**
     * The Map parser.
     */
    private static final MapParser mapParser = new MapParser();

    /**
     * The Adventurer parser.
     */
    private static final AdventurerParser adventurerParser = new AdventurerParser();

    /**
     * Main.
     *
     * @param args the args
     * @throws IOException the io exception
     */
    public static void main(final String[] args) throws IOException {

        System.out.println("Action possible :");
        System.out.println("1 = Démo (Carte pré-défini du sprint 1)");
        System.out.println("2 = chargé carte et aventurier");
        System.out.println("E (Exit)");

        final Scanner keyboard = new Scanner(System.in);
        final String choice = keyboard.nextLine();

        if ("1".equals(choice)) {
            demo();
        } else if ("2".equals(choice)) {
            playWithFile();
        } else if ("E".equals(choice)) {
            return;
        } else {
            System.out.println("\nSaisie incorrect\n");
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
        final Adventurer adventurer = new Adventurer("Toto", 1, 1, Orientation.E, null);

        while (true) {
            System.out.println(
                    String.format("Carte (ligne x colonne) : %sx%s Position aventurier (ligne - colonne - orientation) : %s - %s - %s Nombre de trésors : %s",
                            map.getLineNumber(), map.getColumnNumber(),
                            adventurer.getPosition().getLine(), adventurer.getPosition().getColumn(), adventurer.getOrientation(),
                            adventurer.getTreasureNumber()));
            System.out.println("Action possible : A - D - G - E (Exit)");

            final String action = keyboard.nextLine();

            if ("E".equals(action)) {
                return;
            }

            actionService.executeAction(action, adventurer, map);
        }
    }

    /**
     * Play with file.
     *
     * @throws IOException the io exception
     */
    private static void playWithFile() throws IOException {

        final Scanner keyboard = new Scanner(System.in);

        System.out.println("Chemin accès absolue au fichier de la carte");

        final String mapPath = keyboard.nextLine();
        final Map map = mapParser.parseMapFile(mapPath);


        System.out.println("Chemin accès absolue au fichier des aventuriers");

        final String adventurerPath = keyboard.nextLine();
        final List<Adventurer> adventurers = adventurerParser.parseAdventurerFile(adventurerPath);

        adventurers.forEach(adventurer -> adventurer.getActions().forEach(action -> actionService.executeAction(action, adventurer, map)));

        final String adventurerToString = adventurers.stream().map(Adventurer::toString).collect(Collectors.joining("\n"));

        Files.write(Paths.get("out.txt"), adventurerToString.getBytes());

    }
}
