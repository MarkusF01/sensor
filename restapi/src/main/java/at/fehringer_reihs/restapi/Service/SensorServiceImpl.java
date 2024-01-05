package at.fehringer_reihs.restapi.Service;

import at.fehringer_reihs.restapi.Repository.MeasurementRepository;
import at.fehringer_reihs.restapi.Repository.SensorRepository;
import at.fehringer_reihs.restapi.Repository.model.Measurement;
import at.fehringer_reihs.restapi.Repository.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    public Page<Sensor> getSensors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sensorRepository.findAll(pageable);
    }

    @Override
    public Optional<Sensor> getSensor(long id) {
        return sensorRepository.findById(id);
    }

    @Override
    public Sensor createSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public void deleteSensor(Long id) {
        sensorRepository.deleteById(id);
    }

    @Override
    public Sensor addMeasurementToSensor(Measurement measurement, Sensor sensor) {
        measurement.setSensor(sensor);
        sensor.getMeasurements().add(measurementRepository.save(measurement));
        return sensorRepository.save(sensor);
    }

    @Override
    public Optional<Sensor> updateSensor(Sensor sensor) {
        Optional<Sensor> found = sensorRepository.findById(sensor.getSensorId());
        if(found.isPresent()) {
            Sensor savedSensor = found.get();
            savedSensor.setActive(sensor.isActive());
            savedSensor.setType(sensor.getType());
            savedSensor.setName(sensor.getName());
            savedSensor.setLocation(sensor.getLocation());
            return Optional.of(sensorRepository.save(savedSensor));
        } else {
            return Optional.empty();
        }
    }
}
