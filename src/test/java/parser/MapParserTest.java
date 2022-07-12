package parser;

import entity.Map;
import entity.Mountain;
import entity.Treasure;
import exception.BusinessException;
import exception.TechnicalException;
import org.junit.Assert;
import org.junit.Test;

/**
 * The type Map parser test.
 */
public class MapParserTest {

    private static final String INCORRECT_FILE = "INCORRECT_FILE";

    private MapParser mapParser = new MapParser();

    @Test
    public void parseMapFileOk() throws BusinessException {

        // Given
        final String in = "./src/test/resources/map/map.txt";

        // When
        final Map map = mapParser.parseMapFile(in);

        // Then
        Assert.assertEquals(6, (int) map.getColumnNumber());
        Assert.assertEquals(5, (int) map.getLineNumber());

        Assert.assertNotNull(map.getTreasures());
        Assert.assertEquals(1, map.getTreasures().size());

        final Treasure treasure = map.getTreasures().get(0);

        Assert.assertEquals(1, treasure.getColumn());
        Assert.assertEquals(4, treasure.getLine());
        Assert.assertEquals(2, treasure.getTreasureNumber());

        Assert.assertNotNull(map.getMountains());
        Assert.assertEquals(1, map.getMountains().size());

        final Mountain mountain = map.getMountains().get(0);

        Assert.assertEquals(5, mountain.getColumn());
        Assert.assertEquals(3, mountain.getLine());
    }

    @Test
    public void parseMapFileUnknownInput() throws BusinessException {

        // Given
        final String in = "./src/test/resources/map/toto.txt";

        boolean hasError = false;

        try {
            // When
            mapParser.parseMapFile(in);
        } catch (final TechnicalException e) {
            // Then
            hasError = true;
            Assert.assertEquals("CANNOT_READ_FILE", e.getCodeError());
            Assert.assertEquals(String.format("Cannot read the map file : %s", in), e.getMessageError());
        }

        Assert.assertTrue(hasError);
    }

    @Test
    public void parseMapFileWrongFormat() {

        // Given
        final String in = "./src/test/resources/map/map-wrong-format.txt";

        boolean hasError = false;

        try {
            // When
            mapParser.parseMapFile(in);
        } catch (final BusinessException e) {
            // Then
            hasError = true;
            Assert.assertEquals(INCORRECT_FILE, e.getCodeError());
            Assert.assertEquals(String.format("The line of map file n°2 is incorrect, line = T 4-2, file : %s", in), e.getMessageError());
        }

        Assert.assertTrue(hasError);
    }

    @Test
    public void parseMapFileMultipleMap() {

        // Given
        final String in = "./src/test/resources/map/multiple-map.txt";

        boolean hasError = false;

        try {
            // When
            mapParser.parseMapFile(in);
        } catch (final BusinessException e) {
            // Then
            hasError = true;
            Assert.assertEquals(INCORRECT_FILE, e.getCodeError());
            Assert.assertEquals(String.format("The map has been defined multiple times, file : %s", in), e.getMessageError());
        }

        Assert.assertTrue(hasError);
    }

    @Test
    public void parseMapFileNoMap() {
        // Given
        final String in = "./src/test/resources/map/no-map.txt";

        boolean hasError = false;

        try {
            // When
            mapParser.parseMapFile(in);
        } catch (final BusinessException e) {
            // Then
            hasError = true;
            Assert.assertEquals(INCORRECT_FILE, e.getCodeError());
            Assert.assertEquals(String.format("The map is not defined or poorly defined (ex : C 0 0), file : %s", in), e.getMessageError());
        }

        Assert.assertTrue(hasError);
    }
}
