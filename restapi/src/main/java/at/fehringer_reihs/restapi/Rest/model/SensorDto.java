package at.fehringer_reihs.restapi.Rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class SensorDto {
    private long sensorId;
    private String name;
    private String location;
    private boolean active;
    private SensorType type;
    private List<MeasurementDto> measurements;
}
