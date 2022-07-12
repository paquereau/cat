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
    private final MapParser mapParser;

    /**
     * The Adventurer parser.
     */
    private final AdventurerParser adventurerParser;

    /**
     * The Action service.
     */
    private final ActionService actionService;

    /**
     * The Keyboard.
     */
    private final Scanner keyboard;

    /**
     * Instantiates a new Game service.
     *
     * @param mapParser        the map parser
     * @param adventurerParser the adventurer parser
     * @param actionService    the action service
     * @param keyboard         the keyboard
     */
    public GameService(final MapParser mapParser, final AdventurerParser adventurerParser, final ActionService actionService, final Scanner keyboard) {
        this.mapParser = mapParser;
        this.adventurerParser = adventurerParser;
        this.actionService = actionService;
        this.keyboard = keyboard;
    }

    /**
     * Launch game.
     */
    public String launchGame() throws BusinessException {

        final Map map = createMap();
        final List<Adventurer> adventurers = createAdventurer();

        final ExecutorService executorService = Executors.newCachedThreadPool();

        // Init map position with check
        for (final Adventurer adventurer : adventurers) {
            final Position position = adventurer.getPosition();
            if (position.getColumn() <= 0 || position.getColumn() > map.getColumnNumber()
                    || position.getLine() <= 0 || position.getLine() > map.getLineNumber()) {
                throw new BusinessException("INVALID_INITIAL_POSITION", String.format("The initial position of adventurer %s is invalid", adventurer.getName()));
            }
            map.getPositions().put(adventurer.getName(), adventurer.getPosition());
        }

        // Launch thread
        adventurers.forEach(adventurer -> executorService.execute(() -> {
            System.out.printf("Begin %s%n", adventurer.getName());
            adventurer.getActions().forEach(action -> actionService.executeAction(action, adventurer, map, false));
            System.out.printf("End %s%n", adventurer.getName());
        }));

        executorService.shutdown();

        waitThreadToFinish(executorService);

        return writeResultToFile(adventurers);
    }

    /**
     * Create map map.
     *
     * @return the map
     * @throws BusinessException the business exception
     */
    private Map createMap() throws BusinessException {

        // Read map file
        System.out.println("Absolute path for the map file :");

        final String mapPath = keyboard.nextLine();

        return mapParser.parseMapFile(mapPath);
    }

    /**
     * Create adventurer list.
     *
     * @return the list
     * @throws BusinessException the business exception
     */
    private List<Adventurer> createAdventurer() throws BusinessException {

        // Read adventurer file
        System.out.println("Absolute path for the adventurer path :");

        final String adventurerPath = keyboard.nextLine();

        return adventurerParser.parseAdventurerFile(adventurerPath);
    }

    /**
     * Write result to file
     *
     * @param adventurers the adventurers list
     * @return the string
     */
    private String writeResultToFile(final List<Adventurer> adventurers) {

        final String adventurerToString = adventurers.stream().map(Adventurer::toString).collect(Collectors.joining("\n"));

        final File out = new File("out.txt");

        try {
            Files.write(out.toPath(), adventurerToString.getBytes());
        } catch (final IOException e) {
            throw new TechnicalException("CANNOT_WRITE_FILE", String.format("Cannot write into file : %s", out.getAbsoluteFile()), e);
        }

        return out.getAbsolutePath();
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
            throw new TechnicalException("THREAD_ERROR", "Error during paused thread");
        }
    }
}
