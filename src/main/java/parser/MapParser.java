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

    private static final String INCORRECT_FILE = "INCORRECT_FILE";
    private static final Pattern MAP_LINE_PATTERN = Pattern.compile("^C \\d+ \\d+$");
    private static final Pattern MOUNTAINS_LINE_PATTERN = Pattern.compile("^M \\d+-\\d+$");
    private static final Pattern TREASURE_LINE_PATTERN = Pattern.compile("^T \\d+-\\d+ \\d+$");

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
            lines = Files.lines(Paths.get(mapFilePath)).toList();
        } catch (final IOException e) {
            throw new TechnicalException("CANNOT_READ_FILE", String.format("Cannot read the map file : %s", mapFilePath), e);
        }

        final Map map = new Map();
        final List<Mountain> mountains = new ArrayList<>();
        final List<Treasure> treasures = new ArrayList<>();

        int index = 0;

        for (final String line : lines) {

            index++;

            // Check possible format of line
            if (!(MAP_LINE_PATTERN.matcher(line).matches()
                    || MOUNTAINS_LINE_PATTERN.matcher(line).matches()
                    || TREASURE_LINE_PATTERN.matcher(line).matches())) {
                throw new BusinessException(INCORRECT_FILE, String.format("The line of map file n°%s is incorrect, line = %s, file : %s", index, line, mapFilePath));
            }

            final String[] lineElements = line.split(" ");

            final String lineType = lineElements[0];

            // Choose the type of the line (map, treasure or mountain)
            switch (lineType) {
                case "C" -> {
                    // Check if the map isn't already define
                    if (map.isValid()) {
                        throw new BusinessException(INCORRECT_FILE, String.format("The map has been defined multiple times, file : %s", mapFilePath));
                    }
                    fillMap(lineElements, map);
                }
                case "T" -> treasures.add(createTreasure(lineElements));
                case "M" -> mountains.add(createMountain(lineElements));
                default -> throw new BusinessException(INCORRECT_FILE,
                        String.format("The line of map file n°%s is incorrect, line = %s, instruction '%s' is unknown, file : %s",
                                index, line, lineType, mapFilePath));
            }
        }

        // Check if the map has been define
        if (!map.isValid()) {
            throw new BusinessException(INCORRECT_FILE, String.format("The map is not defined or poorly defined (ex : C 0 0), file : %s", mapFilePath));
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
