package services;

import entity.Adventurer;
import entity.Map;
import entity.Mountain;
import entity.Position;
import entity.Treasure;
import entity.enums.Orientation;
import exception.BusinessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import parser.AdventurerParser;
import parser.MapParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The type Game service test.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @Mock
    private MapParser mapParser;

    @Mock
    private AdventurerParser adventurerParser;

    @Mock
    private ActionService actionService;
    private GameService gameService;

    /**
     * Init.
     */
    @Before
    public void init() {
        final Scanner scanner = new Scanner(new ByteArrayInputStream("test\ntest".getBytes()));
        gameService = new GameService(mapParser, adventurerParser, actionService, scanner);
    }

    /**
     * Launch game test.
     *
     * @throws BusinessException the business exception
     * @throws IOException       the io exception
     */
    @Test
    public void launchGameTest() throws BusinessException, IOException {
        // Given
        Mockito.when(mapParser.parseMapFile(Mockito.any())).thenReturn(createMap());
        Mockito.when(adventurerParser.parseAdventurerFile(Mockito.any())).thenReturn(List.of(createAdventurers(new Position(3, 3))));

        // When
        final String out = gameService.launchGame();

        // Then
        Assert.assertNotNull(out);

        final List<String> lines = Files.readAllLines(Paths.get(out));
        Assert.assertEquals(1, lines.size());
        Assert.assertEquals("Toto 3-3 E 0", lines.get(0));
    }

    /**
     * Launch game test with invalid position.
     */
    @Test()
    public void launchGameTestWithInvalidPosition() {

        boolean hasError = false;

        try {
            // Given
            Mockito.when(mapParser.parseMapFile(Mockito.any())).thenReturn(createMap());
            Mockito.when(adventurerParser.parseAdventurerFile(Mockito.any())).thenReturn(List.of(createAdventurers(new Position(10, 3))));

            // When
            gameService.launchGame();
        } catch (final BusinessException e) {
            // Then
            hasError = true;
            Assert.assertEquals("INVALID_INITIAL_POSITION", e.getCodeError());
            Assert.assertEquals("The initial position of adventurer Toto is invalid", e.getMessageError());
        }

        Assert.assertTrue(hasError);
    }

    /**
     * Create adventurers adventurer.
     *
     * @param position the position
     * @return the adventurer
     */
    private Adventurer createAdventurers(final Position position) {
        return new Adventurer("Toto", position, Orientation.E, List.of("A"));
    }

    /**
     * Create map map.
     *
     * @return the map
     */
    private Map createMap() {

        final List<Mountain> mountains = new ArrayList<>();
        mountains.add(new Mountain(3, 4));

        final List<Treasure> treasures = new ArrayList<>();
        treasures.add(new Treasure(3, 2, 2));

        return new Map(5, 5, mountains, treasures);
    }
}
