package at.fehringer_reihs.restapi.Service;

import at.fehringer_reihs.restapi.Repository.model.Measurement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MeasurementService {

    List<Measurement> getMeasurements();

    Optional<Measurement> getMeasurement(Long measurementId);
}
