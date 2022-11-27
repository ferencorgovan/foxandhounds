package hu.nye.progtech.foxandhounds.persistence;

import java.io.File;

import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.persistence.xml.PersistableMapVO;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class for saving Fox and Hounds game states.
 */
public class XmlGameSavesRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlGameSavesRepository.class);

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;


    public XmlGameSavesRepository(Marshaller marshaller, Unmarshaller unmarshaller) {
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
    }

    /**
     *  Saves a Fox and Hounds game state.
     *
     * @param currentMap Current game state.
     */
    public void saveGameState(MapVO currentMap) {
        try {
            PersistableMapVO persistableMapVO = new PersistableMapVO(currentMap.getMapLength(), currentMap.getMap(),
                    currentMap.getFoxStart(), currentMap.getHoundsStart());
            marshaller.marshal(persistableMapVO, new File("gamestate.xml"));
        } catch (JAXBException e) {
            LOGGER.error("Error while saving game", e);
            throw new RuntimeException("Failed to save game state", e);
        }
    }

    /**
     * Loads a Fox and Hounds game state.
     *
     * @return Loaded game state.
     */
    public MapVO loadGameState() {
        try {
            PersistableMapVO persistableMapVO = (PersistableMapVO) unmarshaller.unmarshal(new File("gamestate.xml"));

            return new MapVO(persistableMapVO.getMapLength(), persistableMapVO.getMap(),
                    persistableMapVO.getFoxStart(), persistableMapVO.getHoundsStart());
        } catch (JAXBException e) {
            LOGGER.error("Error while loading game", e);
            throw new RuntimeException("Failed to load game state", e);
        }
    }
}
