package at.fehringer_reihs.restapi.Service;

import at.fehringer_reihs.restapi.Repository.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MeasurementService {

    /**
     * Get all saved measurements for a certain page
     *
     * @return the found measurements
     */
    Page<Measurement> getMeasurements(int page, int size);

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
