package at.fehringer_reihs.restapi.Service;

import at.fehringer_reihs.restapi.Repository.model.Measurement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MeasurementService {

    /**
     * Get all saved measurements
     *
     * @return the found measurements
     */
    List<Measurement> getMeasurements();

    /**
     * Get Measurement by id
     *
     * @param measurementId the id of the measurement
     * @return the found measurement
     */
    Optional<Measurement> getMeasurement(Long measurementId);

    /**
     * Delete a measurement by id
     *
     * @param measurementId the id of the measurement
     */
    void deleteMeasurement(Long measurementId);
}
