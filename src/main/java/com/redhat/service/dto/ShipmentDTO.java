package com.redhat.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.redhat.domain.Shipment} entity.
 */
@RegisterForReflection
public class ShipmentDTO implements Serializable {
    
    public Long id;

    public Long startPortId;
    public String startPortName;
    public Long endPortId;
    public String endPortName;
    public Long shipId;
    public String shipName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipmentDTO)) {
            return false;
        }

        return id != null && id.equals(((ShipmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ShipmentDTO{" +
            "id=" + id +
            ", startPortId=" + startPortId +
            ", startPortName='" + startPortName + "'" +
            ", endPortId=" + endPortId +
            ", endPortName='" + endPortName + "'" +
            ", shipId=" + shipId +
            ", shipName='" + shipName + "'" +
            "}";
    }
}
