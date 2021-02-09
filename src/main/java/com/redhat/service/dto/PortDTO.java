package com.redhat.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.redhat.domain.Port} entity.
 */
@RegisterForReflection
public class PortDTO implements Serializable {
    
    public Long id;

    public String name;

    public Double latitude;

    public Double longtitude;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortDTO)) {
            return false;
        }

        return id != null && id.equals(((PortDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PortDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", latitude=" + latitude +
            ", longtitude=" + longtitude +
            "}";
    }
}
