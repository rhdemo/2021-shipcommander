package com.redhat.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;
import java.util.Objects;

import com.redhat.domain.enumeration.ShipType;

/**
 * A DTO for the {@link com.redhat.domain.Ship} entity.
 */
@RegisterForReflection
public class ShipDTO implements Serializable {
    
    public Long id;

    public String name;

    public ShipType vesselType;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipDTO)) {
            return false;
        }

        return id != null && id.equals(((ShipDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ShipDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", vesselType='" + vesselType + "'" +
            "}";
    }
}
