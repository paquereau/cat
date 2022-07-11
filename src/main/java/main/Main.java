package main;

import entity.Adventurer;
import entity.Map;
import entity.Mountain;
import entity.Treasure;
import entity.enums.Orientation;
import parser.AdventurerParser;
import parser.MapParser;
import services.ActionService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
    public static void main(final String[] args) throws IOException, InterruptedException {

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
                    String.format("Carte (colonne x ligne) : %sx%s Position aventurier (colonne - ligne - orientation) : %s - %s - %s Nombre de trésors : %s",
                            map.getColumnNumber(), map.getLineNumber(),
                            adventurer.getPosition().getColumn(), adventurer.getPosition().getLine(), adventurer.getOrientation(),
                            adventurer.getTreasureNumber()));
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
     *
     * @throws IOException the io exception
     */
    private static void playWithFile() throws IOException, InterruptedException {

        final Scanner keyboard = new Scanner(System.in);

        System.out.println("Chemin accès absolu au fichier de la carte");

//        final String mapPath = keyboard.nextLine();
//        final Map map = mapParser.parseMapFile(mapPath);

        System.out.println("Chemin accès absolu au fichier des aventuriers");
//
//        final String adventurerPath = keyboard.nextLine();
//        final List<Adventurer> adventurers = adventurerParser.parseAdventurerFile(adventurerPath);

        final Map map = mapParser.parseMapFile("C:\\Users\\fpaquereau\\Documents\\Perso\\Projet\\cat\\src\\main\\resources\\carte.txt");
        final List<Adventurer> adventurers = adventurerParser.parseAdventurerFile("C:\\Users\\fpaquereau\\Documents\\Perso\\Projet\\cat\\src\\main\\resources\\aventurier.txt");

        final ExecutorService executorService = Executors.newCachedThreadPool();

        adventurers.forEach(adventurer -> map.getPositions().put(adventurer.getName(), adventurer.getPosition()));

        adventurers.forEach(adventurer -> executorService.execute(() -> {
            System.out.println(String.format("Début %s", adventurer.getName()));
            adventurer.getActions().forEach(action -> actionService.executeAction(action, adventurer, map, false));
            System.out.println(String.format("Fin %s", adventurer.getName()));
        }));

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        final String adventurerToString = adventurers.stream().map(Adventurer::toString).collect(Collectors.joining("\n"));

        final File out = new File("out.txt");

        Files.write(out.toPath(), adventurerToString.getBytes());

        System.out.println(String.format("Le résultat est disponible ici : %s", out.getAbsoluteFile()));
    }
}
