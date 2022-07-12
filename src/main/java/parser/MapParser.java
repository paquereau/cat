package parser;

import entity.Map;
import entity.Mountain;
import entity.Treasure;
import exception.BusinessException;
import exception.TechnicalException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The type File service.
 */
public class MapParser {

    private static final String INCORRECT_FILE = "FICHIER_INCORRECT";
    private final Pattern mapLinePattern = Pattern.compile("^C \\d+ \\d+$");
    private final Pattern mountainLinePattern = Pattern.compile("^M \\d+-\\d+$");
    private final Pattern treasureLinePattern = Pattern.compile("^T \\d+-\\d+ \\d+$");

    /**
     * Parse map file map.
     *
     * @param mapFilePath the map file path
     * @return the map
     * @throws BusinessException the business exception
     */
    public Map parseMapFile(final String mapFilePath) throws BusinessException {

        final List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get(mapFilePath));
        } catch (final IOException e) {
            throw new TechnicalException("FICHIER_ILLISIBLE", String.format("Impossible de lire le fichier de la carte : %s", mapFilePath), e);
        }

        final Map map = new Map();
        final ArrayList<Mountain> mountains = new ArrayList<>();
        final ArrayList<Treasure> treasures = new ArrayList<>();

        int index = 0;

        for (final String line : lines) {

            index++;

            // Check possible format of line
            if (!(mapLinePattern.matcher(line).matches()
                    || mountainLinePattern.matcher(line).matches()
                    || treasureLinePattern.matcher(line).matches())) {
                throw new BusinessException(INCORRECT_FILE, String.format("La ligne du fichier carte n°%s est incorrecte, ligne = %s, fichier : %s", index, line, mapFilePath));
            }

            final String[] lineElements = line.split(" ");

            final String lineType = lineElements[0];

            // Choose the type of the line (map, treasure or mountain)
            switch (lineType) {
                case "C" -> {
                    // Check if the map isn't already define
                    if (map.isValid()) {
                        throw new BusinessException(INCORRECT_FILE, String.format("La carte a été définie plusieurs fois, fichier : %s", mapFilePath));
                    }
                    fillMap(lineElements, map);
                }
                case "T" -> treasures.add(createTreasure(lineElements));
                case "M" -> mountains.add(createMountain(lineElements));
                default -> throw new BusinessException(INCORRECT_FILE,
                        String.format("La ligne du fichier carte n°%s est incorrecte, ligne = %s, l'instruction '%s' est inconnu, fichier : %s",
                                index, line, lineType, mapFilePath));
            }
        }

        // Check if the map has been define
        if (!map.isValid()) {
            throw new BusinessException(INCORRECT_FILE, String.format("La carte n'a pas été défini ou mal défini (ex : C 0 0), fichier : %s", mapFilePath));
        }

        map.setMountains(mountains);
        map.setTreasures(treasures);

        return map;
    }

    /**
     * Fill map.
     *
     * @param columns the columns
     * @param map     the map
     */
    private void fillMap(final String[] columns, final Map map) {
        map.setColumnNumber(Integer.parseInt(columns[1]));
        map.setLineNumber(Integer.parseInt(columns[2]));
    }

    /**
     * Create mountain mountain.
     *
     * @param columns the columns
     * @return the mountain
     */
    private Mountain createMountain(final String[] columns) {
        final String[] position = columns[1].split("-");
        return new Mountain(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
    }

    /**
     * Create treasure treasure.
     *
     * @param columns the columns
     * @return the treasure
     */
    private Treasure createTreasure(final String[] columns) {
        final String[] position = columns[1].split("-");
        return new Treasure(Integer.parseInt(position[0]), Integer.parseInt(position[1]), Integer.parseInt(columns[2]));
    }
}
