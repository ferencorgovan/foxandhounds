package hu.nye.progtech.foxandhounds.persistence;

import hu.nye.progtech.foxandhounds.model.MapVO;
import hu.nye.progtech.foxandhounds.persistence.xml.PersistableMapVO;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class XmlGameSavesRepositoryTest {
    private XmlGameSavesRepository underTest;

    @Mock
    private Marshaller marshaller;

    @Mock
    private Unmarshaller unmarshaller;

    @Mock
    private MapVO currentMap;

    @Mock
    private PersistableMapVO persistableMapVO;
    private final static int MAP_LENGTH = 4;
    private static final char[][] MAP = {
            {'*', 'H', '*', 'H'},
            {'*', '*', '*', '*'},
            {'*', '*', '*', '*'},
            {'F', '*', '*', '*'},
    };
    private static final String FOX_START = "30";
    private static final String[] HOUNDS_START = {"01", "03"};

    @BeforeEach
    public void setUp() {
        underTest = new XmlGameSavesRepository(marshaller, unmarshaller);
    }

    @Test
    public void testSaveGameStateShouldCallMarshallerWhenThereIsNoException() throws JAXBException {
        // given
        when(currentMap.getMapLength()).thenReturn(MAP_LENGTH);
        when(currentMap.getMap()).thenReturn(MAP);
        when(currentMap.getFoxStart()).thenReturn(FOX_START);
        when(currentMap.getHoundsStart()).thenReturn(HOUNDS_START);

        // when
        underTest.saveGameState(currentMap);

        // then
        PersistableMapVO persistableMapVO = new PersistableMapVO(MAP_LENGTH, MAP, FOX_START, HOUNDS_START);
        verify(marshaller).marshal(eq(persistableMapVO), any(File.class));
        verify(currentMap).getMapLength();
        verify(currentMap).getMap();
        verify(currentMap).getFoxStart();
        verify(currentMap).getHoundsStart();
        verifyNoMoreInteractions(marshaller, unmarshaller, currentMap);
    }

    @Test
    public void testLoadGameStateShouldLoadGameStateWhenThereIsNoException() throws JAXBException {
        // given
        when(persistableMapVO.getMapLength()).thenReturn(MAP_LENGTH);
        when(persistableMapVO.getMap()).thenReturn(MAP);
        when(persistableMapVO.getFoxStart()).thenReturn(FOX_START);
        when(persistableMapVO.getHoundsStart()).thenReturn(HOUNDS_START);
        MapVO expected = new MapVO(MAP_LENGTH, MAP, FOX_START, HOUNDS_START);
        when(unmarshaller.unmarshal(any(File.class))).thenReturn(persistableMapVO);

        // when
        MapVO result = underTest.loadGameState();

        // then
        verify(unmarshaller).unmarshal(any(File.class));
        verify(persistableMapVO).getMapLength();
        verify(persistableMapVO).getMap();
        verify(persistableMapVO).getFoxStart();
        verify(persistableMapVO).getHoundsStart();
        assertEquals(expected, result);
        verifyNoMoreInteractions(marshaller, unmarshaller, persistableMapVO);
    }

    @Test
    public void testSaveGameStateShouldThrowRuntimeExceptionWhenThereIsJAXBException() throws JAXBException {
        // given
        when(currentMap.getMapLength()).thenReturn(MAP_LENGTH);
        when(currentMap.getMap()).thenReturn(MAP);
        when(currentMap.getFoxStart()).thenReturn(FOX_START);
        when(currentMap.getHoundsStart()).thenReturn(HOUNDS_START);
        PersistableMapVO persistableMapVO = new PersistableMapVO(MAP_LENGTH, MAP, FOX_START, HOUNDS_START);
        doThrow(JAXBException.class).when(marshaller).marshal(eq(persistableMapVO), any(File.class));

        // when
        assertThrows(RuntimeException.class, () -> underTest.saveGameState(currentMap));

        // then
        verify(marshaller).marshal(eq(persistableMapVO), any(File.class));
        verify(currentMap).getMapLength();
        verify(currentMap).getMap();
        verify(currentMap).getFoxStart();
        verify(currentMap).getHoundsStart();
        verifyNoMoreInteractions(marshaller, unmarshaller, currentMap);
    }

    @Test
    public void testLoadGameStateShouldThrowRuntimeExceptionWhenThereIsJAXBException() throws JAXBException {
        // given
        when(unmarshaller.unmarshal(any(File.class))).thenThrow(JAXBException.class);

        // when
        assertThrows(RuntimeException.class, () -> underTest.loadGameState());

        // then
        verify(unmarshaller).unmarshal(any(File.class));
        verifyNoMoreInteractions(marshaller, unmarshaller, persistableMapVO);
    }
}