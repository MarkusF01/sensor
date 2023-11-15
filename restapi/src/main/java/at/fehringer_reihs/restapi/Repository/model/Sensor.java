package at.fehringer_reihs.restapi.Repository.model;

import at.fehringer_reihs.restapi.Rest.model.SensorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sensors")
@Getter @Setter
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sensorId;
    private String name;
    private String location;
    private boolean active;
    @Enumerated(EnumType.STRING)
    private SensorType type;
    @OneToMany(mappedBy = "sensor",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Measurement> measurements;

    public Sensor() {
    }

    public Sensor(Long sensorId, String name, String location, boolean active, SensorType type) {
        this.sensorId = sensorId;
        this.name = name;
        this.location = location;
        this.active = active;
        this.type = type;
    }
}