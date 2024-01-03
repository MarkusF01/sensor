package at.fehringer_reihs.restapi.Service;

import at.fehringer_reihs.restapi.Repository.MeasurementRepository;
import at.fehringer_reihs.restapi.Repository.SensorRepository;
import at.fehringer_reihs.restapi.Repository.model.Measurement;
import at.fehringer_reihs.restapi.Repository.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    public List<Sensor> getSensors() {
        List<Sensor> sensors = new ArrayList<>();
        sensorRepository.findAll().forEach(sensors::add);
        return sensors;
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
}
