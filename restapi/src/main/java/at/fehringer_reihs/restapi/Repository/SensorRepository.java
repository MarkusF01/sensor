package at.fehringer_reihs.restapi.Repository;

import at.fehringer_reihs.restapi.Repository.model.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface SensorRepository extends CrudRepository<Sensor, Long> {
    Page<Sensor> findAll(Pageable pageable);
}
