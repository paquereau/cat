package parser;

import entity.Adventurer;
import entity.Position;
import entity.enums.Orientation;
import exception.BusinessException;
import exception.TechnicalException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * The type File service.
 */
public class AdventurerParser {

    private static final String INCORRECT_FILE = "INCORRECT_FILE";

    private Pattern adventurerLinePattern = Pattern.compile("^\\S+ \\d+-\\d+ [NSEO] [ADG]+$");

    /**
     * Parse adventurer file list.
     *
     * @param adventurerFilePath the adventurer file path
     * @return the list
     */
    public List<Adventurer> parseAdventurerFile(final String adventurerFilePath) throws BusinessException {

        final List<String> adventurerLines;

        try {
            adventurerLines = Files.readAllLines(Paths.get(adventurerFilePath));
        } catch (final IOException e) {
            throw new TechnicalException("CANNOT_READ_FILE", String.format("Cannot read the adventurer file : %s", adventurerFilePath), e);
        }

        final List<Adventurer> adventurers = new ArrayList<>();
        final Set<Position> positions = new HashSet<>();
        final Set<String> names = new HashSet<>();

        int index = 0;

        for (final String line : adventurerLines) {
            index++;

            // Check the line format
            if (!adventurerLinePattern.matcher(line).matches()) {
                throw new BusinessException(INCORRECT_FILE,
                        String.format("The line of adventurer file n°%s is incorrect, line = %s, file : %s", index, line, adventurerFilePath));
            }

            // Create adventurer
            final String[] columns = line.split(" ");

            final String[] position = columns[1].split("-");

            final String name = columns[0];
            final Position positionInitial = new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
            final Orientation initialOrientation = Orientation.valueOf(columns[2]);
            final List<String> actions = Arrays.asList(columns[3].split(""));

            adventurers.add(new Adventurer(name, positionInitial, initialOrientation, actions));
            positions.add(positionInitial);
            names.add(name);
        }

        // If there is not the same number of positions than adventurers
        if (positions.size() != adventurers.size()) {
            // Then error
            throw new BusinessException(INCORRECT_FILE, String.format("Multiple adventurer with the same initial position, file : %s", adventurerFilePath));
        }

        // If there is not the same number of names than adventurers
        if (names.size() != adventurers.size()) {
            // Then error
            throw new BusinessException(INCORRECT_FILE, String.format("Multiple adventurer with the same name, file : %s", adventurerFilePath));
        }

        return adventurers;
    }
}
