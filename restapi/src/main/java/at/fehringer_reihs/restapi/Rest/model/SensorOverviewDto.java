package at.fehringer_reihs.restapi.Rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SensorOverviewDto {

    private long sensorId;
    private String name;
    private String location;
    private boolean active;
    private SensorType type;

    public SensorOverviewDto() {
    }
}
