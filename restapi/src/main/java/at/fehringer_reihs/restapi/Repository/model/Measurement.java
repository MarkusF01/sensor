package at.fehringer_reihs.restapi.Repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurements")
@Getter @Setter
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementId;
    private LocalDateTime timestamp;
    private double temperature;
    private double humidity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensorId", nullable = false)
    private Sensor sensor;

    public Measurement(Long measurementId, LocalDateTime timestamp, double temperature, double humidity) {
        this.measurementId = measurementId;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Measurement() {
    }
}
