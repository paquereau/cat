package services;

import entity.Adventurer;
import entity.Map;
import entity.Position;
import exception.BusinessException;
import exception.TechnicalException;
import parser.AdventurerParser;
import parser.MapParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * The type Game service.
 */
public class GameService {

    /**
     * The Map parser.
     */
    private MapParser mapParser;

    /**
     * The Action service.
     */
    private ActionService actionService;

    /**
     * The Adventurer parser.
     */
    private AdventurerParser adventurerParser;

    /**
     * Instantiates a new Game service.
     *
     * @param actionService the action service
     */
    public GameService(final ActionService actionService) {
        this.actionService = actionService;
        this.mapParser = new MapParser();
        this.adventurerParser = new AdventurerParser();
    }

    /**
     * Launch game.
     */
    public void launchGame() {

        try {
            final Scanner keyboard = new Scanner(System.in);

            // Read map file
            System.out.println("Chemin accès absolu au fichier de la carte");

            final String mapPath = keyboard.nextLine();
            final Map map = mapParser.parseMapFile(mapPath);

            // Read adventurer file
            System.out.println("Chemin accès absolu au fichier des aventuriers");

            final String adventurerPath = keyboard.nextLine();
            final List<Adventurer> adventurers = adventurerParser.parseAdventurerFile(adventurerPath);

            final ExecutorService executorService = Executors.newCachedThreadPool();

            // Init map position with check
            for (final Adventurer adventurer : adventurers) {
                final Position position = adventurer.getPosition();
                if (position.getColumn() <= 0 || position.getColumn() > map.getColumnNumber()
                        || position.getLine() <= 0 || position.getLine() > map.getLineNumber()) {
                    throw new BusinessException("INITIAL_POSTION_INVALIDE", String.format("La position initial de l'aventurier %s est invalide", adventurer.getName()));
                }
                map.getPositions().put(adventurer.getName(), adventurer.getPosition());
            }

            // Launch thread
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
    private void writeResultToFile(final List<Adventurer> adventurers) {

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
    private void waitThreadToFinish(final ExecutorService executorService) {
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (final InterruptedException e) {
            throw new TechnicalException("ERREUR_THREAD", "Erreur lors de l'attente de la fin des threads");
        }
    }
}
