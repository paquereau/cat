package parser;

import entity.Adventurer;
import exception.BusinessException;
import exception.TechnicalException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * The type Adventurer parser test.
 */
public class AdventurerParserTest {

    private static final String INCORRECT_FILE = "FICHIER_INCORRECT";

    private AdventurerParser adventurerParser = new AdventurerParser();

    /**
     * Parse adventurer file ok.
     *
     * @throws BusinessException the business exception
     */
    @Test
    public void parseAdventurerFileOk() throws BusinessException {

        // Given
        final String in = "./src/test/resources/adventurer/adventurer.txt";

        // When
        final List<Adventurer> adventurers = adventurerParser.parseAdventurerFile(in);

        // Then
        Assert.assertEquals(2, adventurers.size());
    }

    /**
     * Parse adventurer file unknown input.
     *
     * @throws BusinessException the business exception
     */
    @Test
    public void parseAdventurerFileUnknownInput() throws BusinessException {

        // Given
        final String in = "./src/test/resources/adventurer/toto.txt";

        boolean hasError = false;

        try {
            // When
            adventurerParser.parseAdventurerFile(in);
        } catch (final TechnicalException e) {
            // Then
            hasError = true;
            Assert.assertEquals("FICHIER_ILLISIBLE", e.getCodeError());
            Assert.assertEquals(String.format("Impossible de lire le fichier des aventuriers : %s", in), e.getMessageError());
        }

        Assert.assertTrue(hasError);
    }

    /**
     * Parse adventurer file wrong format.
     */
    @Test
    public void parseAdventurerFileWrongFormat() {

        // Given
        final String in = "./src/test/resources/adventurer/adventurer-wrong-format.txt";

        boolean hasError = false;

        try {
            // When
            adventurerParser.parseAdventurerFile(in);
        } catch (final BusinessException e) {
            // Then
            hasError = true;
            Assert.assertEquals(INCORRECT_FILE, e.getCodeError());
            Assert.assertEquals(String.format("La ligne du fichier aventurier n°1 est incorrecte, ligne = toto 1-1 S T, fichier : %s", in), e.getMessageError());
        }

        Assert.assertTrue(hasError);
    }

    /**
     * Parse adventurer file multiple name ko.
     */
    @Test
    public void parseAdventurerFileMultipleNameKo() {

        // Given
        final String in = "./src/test/resources/adventurer/multiple-adventurer-name-ko.txt";

        boolean hasError = false;

        try {
            // When
            adventurerParser.parseAdventurerFile(in);
        } catch (final BusinessException e) {
            // Then
            hasError = true;
            Assert.assertEquals(INCORRECT_FILE, e.getCodeError());
            Assert.assertEquals(String.format("Plusieurs aventuriers avec le même nom, fichier : %s", in), e.getMessageError());
        }

        Assert.assertTrue(hasError);
    }

    /**
     * Parse adventurer file multiple position ko.
     */
    @Test
    public void parseAdventurerFileMultiplePositionKo() {
        // Given
        final String in = "./src/test/resources/adventurer/multiple-adventurer-position-ko.txt";

        boolean hasError = false;

        try {
            // When
            adventurerParser.parseAdventurerFile(in);
        } catch (final BusinessException e) {
            // Then
            hasError = true;
            Assert.assertEquals(INCORRECT_FILE, e.getCodeError());
            Assert.assertEquals(String.format("Plusieurs aventuriers sur la même case initial, fichier : %s", in), e.getMessageError());
        }

        Assert.assertTrue(hasError);
    }
}
