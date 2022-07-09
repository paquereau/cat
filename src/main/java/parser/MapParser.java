package parser;

import entity.Map;
import entity.Mountain;
import entity.Treasure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The type File service.
 */
public class MapParser {

    private static final String INCORRECT_FILE = "Fichier incorrect";

    /**
     * Parse map file map.
     *
     * @param mapFilePath the map file path
     * @return the map
     * @throws IOException the io exception
     */
    public Map parseMapFile(final String mapFilePath) throws IOException {

        final List<String> lines = Files.readAllLines(Paths.get(mapFilePath));

        final Map map = new Map();
        final ArrayList<Mountain> mountains = new ArrayList<>();
        final ArrayList<Treasure> treasures = new ArrayList<>();

        lines.forEach(line -> {

                    final String[] lineElements = line.split(" ");

                    if (lineElements.length > 0) {

                        final String lineType = lineElements[0];

                        switch (lineType) {
                            case "C":
                                fillMap(lineElements, map);
                                break;
                            case "T":
                                treasures.add(createTreasure(lineElements));
                                break;
                            case "M":
                                mountains.add(createMountain(lineElements));
                                break;
                            default:
                                throw new RuntimeException(INCORRECT_FILE);
                        }
                    }
                }
        );

        if (!map.isValid()) {
            throw new RuntimeException(INCORRECT_FILE);
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

        if (columns.length != 3 || map.isValid()) {
            throw new RuntimeException(INCORRECT_FILE);
        }

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

        if (columns.length != 2) {
            throw new RuntimeException(INCORRECT_FILE);
        }

        final String[] position = columns[1].split("-");

        if (position.length != 2) {
            throw new RuntimeException(INCORRECT_FILE);
        }

        return new Mountain(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
    }

    /**
     * Create treasure treasure.
     *
     * @param columns the columns
     * @return the treasure
     */
    private Treasure createTreasure(final String[] columns) {

        if (columns.length != 3) {
            throw new RuntimeException(INCORRECT_FILE);
        }

        final String[] position = columns[1].split("-");

        if (position.length != 2) {
            throw new RuntimeException(INCORRECT_FILE);
        }

        return new Treasure(Integer.parseInt(position[0]), Integer.parseInt(position[1]), Integer.parseInt(columns[2]));
    }
}
