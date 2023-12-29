package at.fehringer_reihs.restapi.Service;

import at.fehringer_reihs.restapi.Repository.MeasurementRepository;
import at.fehringer_reihs.restapi.Repository.model.Measurement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementServiceImplTest {
    @Mock
    private MeasurementRepository measurementRepository;
    @InjectMocks
    private MeasurementServiceImpl measurementService;


    @Test
    public void testGetMeasurement() {
        Measurement measurement = new Measurement();
        long id = 1L;
        when(measurementRepository.findById(id)).thenReturn(Optional.of(measurement));

        Optional<Measurement> found = measurementService.getMeasurement(id);

        assertTrue(found.isPresent());
        assertEquals(measurement, found.get());
        verify(measurementRepository).findById(id);
    }
}
