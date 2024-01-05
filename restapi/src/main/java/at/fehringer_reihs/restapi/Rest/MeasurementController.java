package at.fehringer_reihs.restapi.Rest;

import at.fehringer_reihs.restapi.Repository.model.Measurement;
import at.fehringer_reihs.restapi.Rest.model.MeasurementDto;
import at.fehringer_reihs.restapi.Rest.model.PaginatedResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Get all measurements in a paginated response", description = "Get all measurements from all sensors, the response is paginated, the list of sensors varies with the given page size.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All saved measurements successfully returned", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MeasurementDto.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Query params page and size are needed"),
            @ApiResponse(responseCode = "500", description = "An error occurred while getting the measurements"),
    })
    @GetMapping
    public ResponseEntity<PaginatedResponse<MeasurementDto>> getMeasurements(
            @Parameter(description = "The current page to get data for") @RequestParam("page") Integer page,
            @Parameter(description = "How much data should be displayed per page") @RequestParam("size") Integer pageSize) {
        Type listType = new TypeToken<List<MeasurementDto>>() {
        }.getType();
        if(pageSize == null || page == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<Measurement> foundPage = measurementService.getMeasurements(page, pageSize);

        PaginatedResponse measurementPageDto = PaginatedResponse.builder()
                .pageSize(foundPage.getSize())
                .totalResults(foundPage.getTotalElements())
                .currentPage(foundPage.getNumber())
                .content(modelMapper.map(foundPage.getContent(), listType))
                .build();

        return ResponseEntity.ok(measurementPageDto);
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
