package com.redhat.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.redhat.domain.SensorReadings} entity.
 */
@RegisterForReflection
public class SensorReadingsDTO implements Serializable {
    
    public Long id;

    public Double temperature;

    public Double latitude;

    public Double longtitude;

    public Double humidityRate;

    public Long containerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SensorReadingsDTO)) {
            return false;
        }

        return id != null && id.equals(((SensorReadingsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SensorReadingsDTO{" +
            "id=" + id +
            ", temperature=" + temperature +
            ", latitude=" + latitude +
            ", longtitude=" + longtitude +
            ", humidityRate=" + humidityRate +
            ", containerId=" + containerId +
            "}";
    }
}
