package at.fehringer_reihs.restapi.Service;

import at.fehringer_reihs.restapi.Repository.MeasurementRepository;
import at.fehringer_reihs.restapi.Repository.model.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MeasurementServiceImpl implements MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    public List<Measurement> getMeasurements() {
        List<Measurement> measurements = new ArrayList<>();
        measurementRepository.findAll().forEach(measurements::add);
        return measurements;
    }

    @Override
    public Optional<Measurement> getMeasurement(Long measurementId) {
        return measurementRepository.findById(measurementId);
    }
}
