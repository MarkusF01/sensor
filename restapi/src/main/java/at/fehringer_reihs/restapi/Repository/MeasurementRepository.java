package at.fehringer_reihs.restapi.Repository;

import at.fehringer_reihs.restapi.Repository.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MeasurementRepository extends CrudRepository<Measurement, Long> {
    Page<Measurement> findAll(Pageable pageable);
}
