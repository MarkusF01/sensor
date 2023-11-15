package at.fehringer_reihs.restapi.Rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SensorDto {

    private long sensorId;
    private String name;
    private String location;
    private boolean active;
    private SensorType type;

    public SensorDto() {
    }

    public SensorDto(long sensorId, String name, String location, boolean active, SensorType type) {
        this.sensorId = sensorId;
        this.name = name;
        this.location = location;
        this.active = active;
        this.type = type;
    }
}
