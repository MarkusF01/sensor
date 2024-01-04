package at.fehringer_reihs.restapi.Rest;

import at.fehringer_reihs.restapi.Repository.model.Measurement;
import at.fehringer_reihs.restapi.Rest.model.MeasurementDto;
import at.fehringer_reihs.restapi.Service.MeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Delete a measurement", description = "Delete a specific measurement by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The measurement was deleted", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred while deleting the measurement", content = @Content),
    })
    @DeleteMapping("/{id}")
    public void deleteMeasurement(@Parameter(description = "The id of the measurement to delete") @PathVariable Long id) {
        measurementService.deleteMeasurement(id);
    }
}
