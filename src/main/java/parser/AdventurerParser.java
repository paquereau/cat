package parser;

import entity.Adventurer;
import entity.enums.Orientation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type File service.
 */
public class AdventurerParser {

    private static final String INCORRECT_FILE = "Fichier incorrect";

    /**
     * Parse adventurer file list.
     *
     * @param adventurerFilePath the adventurer file path
     * @return the list
     */
    public List<Adventurer> parseAdventurerFile(final String adventurerFilePath) throws IOException {

        final List<String> adventurerLines = Files.readAllLines(Paths.get(adventurerFilePath));
        final List<Adventurer> adventurers = new ArrayList<>();

        adventurerLines.stream().map(line -> line.split(" ")).forEach(columns -> {

            if (columns.length != 4) {
                throw new RuntimeException(INCORRECT_FILE);
            }

            final String[] position = columns[1].split("-");

            if (position.length != 2) {
                throw new RuntimeException(INCORRECT_FILE);
            }

            final String name = columns[0];
            final int initialColumn = Integer.parseInt(position[0]);
            final int initialLine = Integer.parseInt(position[1]);
            final Orientation initialOrientation = Orientation.valueOf(columns[2]);
            final List<String> actions = Arrays.asList(columns[3].split(""));

            adventurers.add(new Adventurer(name, initialColumn, initialLine, initialOrientation, actions));
        });

        return adventurers;
    }
}
