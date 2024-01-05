package at.fehringer_reihs.restapi.Service;

import at.fehringer_reihs.restapi.Repository.model.Measurement;
import at.fehringer_reihs.restapi.Repository.model.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SensorService {

    /**
     * Get a specified page of sensors
     *
     * @param page the page to request
     * @param size the size of the page
     * @return found sensors
     */
    Page<Sensor> getSensors(int page, int size);

    /**
     * Get a sensor by id
     *
     * @param id the id of the sensor
     * @return the sensor that was found
     */
    Optional<Sensor> getSensor(long id);

    /**
     * Create a new sensor
     *
     * @param sensor the sensor to create
     * @return the created sensor
     */
    Sensor createSensor(Sensor sensor);

    /**
     * Delete a sensor by id
     *
     * @param id the sensor to delete
     */
    void deleteSensor(Long id);

    /**
     * Add a measurement to a sensor
     *
     * @param measurement the measurement to add to the sensor
     * @param sensor the sensor
     * @return the sensor with the added measurement
     */
    Sensor addMeasurementToSensor(Measurement measurement, Sensor sensor);


    /**
     * Update a sensor
     *
     * @param sensor the sensor to update
     * @return the updated sensor
     */
    Optional<Sensor> updateSensor(Sensor sensor);

}
