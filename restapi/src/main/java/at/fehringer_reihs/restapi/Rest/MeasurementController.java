package at.fehringer_reihs.restapi.Rest;

import at.fehringer_reihs.restapi.Repository.model.Measurement;
import at.fehringer_reihs.restapi.Rest.model.MeasurementDto;
import at.fehringer_reihs.restapi.Service.MeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/measurements")
@Tag(name = "Measurement-Controller", description = "Controller responsible for handling measurement operations")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper){
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all measurements", description = "Get all measurements from all sensors.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All saved measurements successfully returned", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MeasurementDto.class)))
            }),
            @ApiResponse(responseCode = "500", description = "An error occurred while getting the measurements",
                    content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<MeasurementDto>> getMeasurements() {
        Type listType = new TypeToken<List<MeasurementDto>>() {
        }.getType();
        List<Measurement> measurements = measurementService.getMeasurements();
        List<MeasurementDto> mappedMeasurements = modelMapper.map(measurements, listType);
        return ResponseEntity.ok(mappedMeasurements);
    }

    @Operation(summary = "Get a measurement", description = "Get a measurement by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Measurement found", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MeasurementDto.class)))
            }),
            @ApiResponse(responseCode = "500", description = "An error occurred while getting the measurement",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public MeasurementDto getMeasurementById(@PathVariable Long id){
        return modelMapper.map(measurementService.getMeasurement(id), MeasurementDto.class);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return new ResponseEntity<>("Hello here is your measurement endpoint", HttpStatus.ACCEPTED);
    }
}
