package at.fehringer_reihs.restapi.Rest;

import at.fehringer_reihs.restapi.Repository.model.Measurement;
import at.fehringer_reihs.restapi.Repository.model.Sensor;
import at.fehringer_reihs.restapi.Rest.model.MeasurementDto;
import at.fehringer_reihs.restapi.Rest.model.SensorDto;
import at.fehringer_reihs.restapi.Service.SensorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sensors")
//TODO open api impl
public class SensorController {

    private SensorService sensorService;
    private ModelMapper modelMapper;

    @Value("${test.testtext}")
    private String text;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    //TODO response objects - what kind
    @PostMapping
    public ResponseEntity<SensorDto> createSensor(@RequestBody SensorDto sensorDto) {
        Sensor createdSensor = sensorService.createSensor(modelMapper.map(sensorDto, Sensor.class));
        SensorDto mappedSensor = modelMapper.map(createdSensor, SensorDto.class);
        return new ResponseEntity<>(mappedSensor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDto> getSensor(@PathVariable Long id) {
        Optional<Sensor> foundSensor = sensorService.getSensor(id);
        if (foundSensor.isPresent()) {
            SensorDto mappedSensor = modelMapper.map(foundSensor.get(), SensorDto.class);
            return new ResponseEntity<>(mappedSensor, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<SensorDto>> getSensors() {
        Type listType = new TypeToken<List<SensorDto>>() {}.getType();
        List<SensorDto> mappedSensors = modelMapper.map(sensorService.getSensors(), listType);
        return new ResponseEntity<>(mappedSensors, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public void deleteSensor(@PathVariable Long id) {
        sensorService.deleteSensor(id);
    }

    @PostMapping ("/{id}/measurements")
    public ResponseEntity<SensorDto> addMeasurementFromSensor(@PathVariable Long id, @RequestBody MeasurementDto measurementDto) {
        Optional<Sensor> foundSensor = sensorService.getSensor(id);
        if(foundSensor.isPresent()){
            Measurement mappedInput = modelMapper.map(measurementDto, Measurement.class);
            Sensor updatedSensor = sensorService.addMeasurementToSensor(mappedInput, foundSensor.get());
            SensorDto mappedSensor = modelMapper.map(updatedSensor, SensorDto.class);
            return new ResponseEntity<>(mappedSensor, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping ("/{id}/measurements")
    public ResponseEntity<List<MeasurementDto>> getMeasurementFromSensor(@PathVariable Long id) {
        Optional<Sensor> foundSensor = sensorService.getSensor(id);
        if(foundSensor.isPresent()){
            List<Measurement> measurements = foundSensor.get().getMeasurements();
            Type listType = new TypeToken<List<MeasurementDto>>() {}.getType();
            List<MeasurementDto> mappedMeasurements = modelMapper.map(measurements, listType);
            return new ResponseEntity<>(mappedMeasurements, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return new ResponseEntity<>(
                "Hello here is your sensor endpoint and test.textext from config server is: "+text,
                HttpStatus.ACCEPTED
        );
    }
}
