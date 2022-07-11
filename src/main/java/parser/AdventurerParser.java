package parser;

import entity.Adventurer;
import entity.enums.Orientation;
import exception.BusinessException;
import exception.TechnicalException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The type File service.
 */
public class AdventurerParser {

    private static final String INCORRECT_FILE = "Fichier incorrect";

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
        } catch (IOException e) {
            throw new TechnicalException("FICHIER_ILLISIBLE", String.format("Impossible de lire le fichier des aventuriers : %s", adventurerFilePath), e);
        }
        final List<Adventurer> adventurers = new ArrayList<>();

        int index = 0;

        for (final String line : adventurerLines) {
            index++;

            if (!adventurerLinePattern.matcher(line).matches()) {
                throw new BusinessException(INCORRECT_FILE, String.format("La ligne du fichier aventurier n°%s est incorrecte, ligne = %s", index, line));
            }

            final String[] columns = line.split(" ");

            final String[] position = columns[1].split("-");

            final String name = columns[0];
            final int initialColumn = Integer.parseInt(position[0]);
            final int initialLine = Integer.parseInt(position[1]);
            final Orientation initialOrientation = Orientation.valueOf(columns[2]);
            final List<String> actions = Arrays.asList(columns[3].split(""));

            adventurers.add(new Adventurer(name, initialColumn, initialLine, initialOrientation, actions));
        }

        return adventurers;
    }
}
