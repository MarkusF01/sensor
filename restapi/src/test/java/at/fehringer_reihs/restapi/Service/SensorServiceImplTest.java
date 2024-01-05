package at.fehringer_reihs.restapi.Service;

import at.fehringer_reihs.restapi.Repository.MeasurementRepository;
import at.fehringer_reihs.restapi.Repository.SensorRepository;
import at.fehringer_reihs.restapi.Repository.model.Measurement;
import at.fehringer_reihs.restapi.Repository.model.Sensor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SensorServiceImplTest {
    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private MeasurementRepository measurementRepository;
    @InjectMocks
    private SensorServiceImpl sensorService;


    @Test
    public void testGetSensors() {
        List<Sensor> sensors = Arrays.asList(new Sensor(), new Sensor());
        Pageable pageable = PageRequest.of(0, sensors.size());
        Page<Sensor> sensorPage = new PageImpl<>(sensors, pageable, sensors.size());
        when(sensorRepository.findAll(any())).thenReturn(sensorPage);

        Page<Sensor> found = sensorService.getSensors(1, 10);

        assertNotNull(found);
        assertEquals(2, found.getSize());
        verify(sensorRepository).findAll(any());
    }

    @Test
    public void testGetSensor() {
        Sensor sensor = new Sensor();
        long id = 1L;
        when(sensorRepository.findById(id)).thenReturn(Optional.of(sensor));

        Optional<Sensor> found = sensorService.getSensor(id);

        assertTrue(found.isPresent());
        assertEquals(sensor, found.get());
        verify(sensorRepository).findById(id);
    }

    @Test
    public void testCreateSensor() {
        Sensor sensor = new Sensor();
        when(sensorRepository.save(sensor)).thenReturn(sensor);

        Sensor created = sensorService.createSensor(sensor);

        assertNotNull(created);
        verify(sensorRepository).save(sensor);
    }

    @Test
    public void testDeleteSensor() {
        Long id = 1L;
        doNothing().when(sensorRepository).deleteById(id);

        sensorService.deleteSensor(id);

        verify(sensorRepository).deleteById(id);
    }

    @Test
    public void testAddMeasurementToSensor() {
        Sensor sensor = new Sensor();
        sensor.setMeasurements(new ArrayList<>());
        Measurement measurement = new Measurement();

        when(sensorRepository.save(sensor)).thenReturn(sensor);
        when(measurementRepository.save(measurement)).thenReturn(measurement);

        Sensor updatedSensor = sensorService.addMeasurementToSensor(measurement, sensor);

        assertNotNull(updatedSensor.getMeasurements());
        assertTrue(updatedSensor.getMeasurements().contains(measurement));
        verify(sensorRepository).save(sensor);
    }
}
