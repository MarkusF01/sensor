package at.fehringer_reihs.restapi.Rest;

import at.fehringer_reihs.restapi.Rest.model.MeasurementDto;
import at.fehringer_reihs.restapi.Service.MeasurementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private MeasurementService measurementService;
    private ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper){
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public MeasurementDto getMeasurementFromSensor(@PathVariable Long id){
        return modelMapper.map(measurementService.getMeasurement(id), MeasurementDto.class);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return new ResponseEntity<>("Hello here is your measurment endpoint", HttpStatus.ACCEPTED);
    }
}
