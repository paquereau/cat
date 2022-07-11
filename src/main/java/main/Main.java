package main;

import entity.Adventurer;
import entity.Map;
import entity.Mountain;
import entity.Treasure;
import entity.enums.Orientation;
import exception.BusinessException;
import exception.TechnicalException;
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
                playWithFile();
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
        final Adventurer adventurer = new Adventurer("Toto", 1, 1, Orientation.E, null);

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

        try {
            final Scanner keyboard = new Scanner(System.in);

            System.out.println("Chemin accès absolu au fichier de la carte");

            final String mapPath = keyboard.nextLine();
            final Map map = mapParser.parseMapFile(mapPath);

            System.out.println("Chemin accès absolu au fichier des aventuriers");

            final String adventurerPath = keyboard.nextLine();
            final List<Adventurer> adventurers = adventurerParser.parseAdventurerFile(adventurerPath);

            final ExecutorService executorService = Executors.newCachedThreadPool();

            adventurers.forEach(adventurer -> map.getPositions().put(adventurer.getName(), adventurer.getPosition()));

            adventurers.forEach(adventurer -> executorService.execute(() -> {
                System.out.printf("Début %s%n", adventurer.getName());
                adventurer.getActions().forEach(action -> actionService.executeAction(action, adventurer, map, false));
                System.out.printf("Fin %s%n", adventurer.getName());
            }));

            executorService.shutdown();

            waitThreadToFinish(executorService);

            writeResultToFile(adventurers);

        } catch (final BusinessException e) {
            System.out.printf("%n%s : %s%n", e.getCodeError(), e.getMessageError());
            e.printStackTrace();
        }
    }

    /**
     * Write result to file
     *
     * @param adventurers the adventurers list
     */
    private static void writeResultToFile(final List<Adventurer> adventurers) {

        final String adventurerToString = adventurers.stream().map(Adventurer::toString).collect(Collectors.joining("\n"));

        final File out = new File("out.txt");

        try {
            Files.write(out.toPath(), adventurerToString.getBytes());
        } catch (final IOException e) {
            throw new TechnicalException("", String.format("Impossible d'écrire dans le fichier %s", out.getAbsoluteFile()), e);
        }

        System.out.printf("Le résultat est disponible ici : %s%n", out.getAbsoluteFile());
    }

    /**
     * Wait thread to finish.
     *
     * @param executorService the executor service
     */
    private static void waitThreadToFinish(final ExecutorService executorService) {
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (final InterruptedException e) {
            throw new TechnicalException("ERREUR_THREAD", "Erreur lors de l'attente de la fin des threads");
        }
    }
}
