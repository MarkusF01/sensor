package at.fehringer_reihs.restapi.Rest;

import at.fehringer_reihs.restapi.Repository.model.Measurement;
import at.fehringer_reihs.restapi.Repository.model.Sensor;
import at.fehringer_reihs.restapi.Rest.model.MeasurementDto;
import at.fehringer_reihs.restapi.Rest.model.SensorDto;
import at.fehringer_reihs.restapi.Rest.model.SensorOverviewDto;
import at.fehringer_reihs.restapi.Service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Sensor-Controller", description = "Controller responsible for handling all sensor operations")
public class SensorController {

    private SensorService sensorService;
    private ModelMapper modelMapper;
    private int numberRequests = 0;

    @Value("${server.port}")
    private String port;

    @Value("${test.testtext}")
    private String text;
    @Value("${test.rest-test}")
    private String restText;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Create a sensor", description = "A sensor is created with the given attributes and is saved in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The sensor was found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SensorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "An error occurred while creating the sensor",
                    content = @Content),
    })
    @PostMapping
    public ResponseEntity<SensorDto> createSensor(@Parameter(description = "The Sensor to create") @RequestBody @Valid SensorDto sensorDto) {
        Sensor createdSensor = sensorService.createSensor(modelMapper.map(sensorDto, Sensor.class));
        SensorDto mappedSensor = modelMapper.map(createdSensor, SensorDto.class);
        return ResponseEntity.ok(mappedSensor);
    }

    @Operation(summary = "Get a sensor by id", description = "Get a specific sensor by id that is saved in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The sensor was found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SensorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "No sensor was found with the given id",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<SensorDto> getSensor(@Parameter(description = "The id of the sensor", required = true) @PathVariable Long id) {
        Optional<Sensor> foundSensor = sensorService.getSensor(id);
        if (foundSensor.isPresent()) {
            SensorDto mappedSensor = modelMapper.map(foundSensor.get(), SensorDto.class);
            return new ResponseEntity<>(mappedSensor, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all sensors", description = "Get all sensor that are saved in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All saved sensor successfully returned", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SensorDto.class)))
            }),
            @ApiResponse(responseCode = "500", description = "An error occurred while creating the sensor",
                    content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<SensorOverviewDto>> getSensors() {
        Type listType = new TypeToken<List<SensorOverviewDto>>() {
        }.getType();
        List<Sensor> sensors = sensorService.getSensors();
        List<SensorOverviewDto> mappedSensors = modelMapper.map(sensors, listType);
        return ResponseEntity.ok(mappedSensors);
    }

    @Operation(summary = "Delete a sensor", description = "Delete a specific sensor that is saved in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The sensor was deleted", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred while deleting the sensor", content = @Content),
    })
    @DeleteMapping("/{id}")
    public void deleteSensor(@PathVariable Long id) {
        sensorService.deleteSensor(id);
    }

    @Operation(summary = "Add a measurement", description = "Add a measurement to (from) a sensor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The measurement was added successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SensorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "An error occurred while adding the measurement",
                    content = @Content),
    })
    @PostMapping("/{id}/measurements")
    public ResponseEntity<SensorDto> addMeasurementFromSensor(@Parameter(description = "The id of the sensor to add the measurement to") @PathVariable Long id,
                                                              @Parameter(description = "The measurement to add") @RequestBody MeasurementDto measurementDto) {
        Optional<Sensor> foundSensor = sensorService.getSensor(id);
        if (foundSensor.isPresent()) {
            Measurement mappedInput = modelMapper.map(measurementDto, Measurement.class);
            Sensor updatedSensor = sensorService.addMeasurementToSensor(mappedInput, foundSensor.get());
            SensorDto mappedSensor = modelMapper.map(updatedSensor, SensorDto.class);
            return new ResponseEntity<>(mappedSensor, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get measurements from a sensor", description = "Get all measurements from a sensor that are saved in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All measurements from the sensors were returned successfully", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MeasurementDto.class)))
            }),
            @ApiResponse(responseCode = "404", description = "No Sensor was found with the given id", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred getting the measurements from the sensor", content = @Content),
    })
    @GetMapping("/{id}/measurements")
    public ResponseEntity<List<MeasurementDto>> getMeasurementsFromSensor(@Parameter(description = "Sensor id to retrieve the measurements by") @PathVariable Long id) {
        Optional<Sensor> foundSensor = sensorService.getSensor(id);
        if (foundSensor.isPresent()) {
            List<Measurement> measurements = foundSensor.get().getMeasurements();
            Type listType = new TypeToken<List<MeasurementDto>>() {
            }.getType();
            List<MeasurementDto> mappedMeasurements = modelMapper.map(measurements, listType);
            return new ResponseEntity<>(mappedMeasurements, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Test endpoint", description = "This is a test endpoint so we can test the connection.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error", content = @Content),
    })
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        System.out.println("This is my " + ++numberRequests + ". request!");
        return new ResponseEntity<>(
                "Here is your sensor endpoint from port: " + port + ", and --testtext-- from config server is: " + text + ", and --rest-test-- is: " + restText,
                HttpStatus.ACCEPTED
        );
    }
}
