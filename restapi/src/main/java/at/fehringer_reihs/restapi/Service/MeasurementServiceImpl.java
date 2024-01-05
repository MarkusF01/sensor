package at.fehringer_reihs.restapi.Service;

import at.fehringer_reihs.restapi.Repository.MeasurementRepository;
import at.fehringer_reihs.restapi.Repository.model.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MeasurementServiceImpl implements MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    public Page<Measurement> getMeasurements(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return measurementRepository.findAll(pageable);
    }

    @Override
    public Optional<Measurement> getMeasurement(Long measurementId) {
        return measurementRepository.findById(measurementId);
    }

    @Override
    public void deleteMeasurement(Long measurementId) {
        measurementRepository.deleteById(measurementId);
    }
}
