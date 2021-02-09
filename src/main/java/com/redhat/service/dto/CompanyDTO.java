package com.redhat.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.redhat.domain.Company} entity.
 */
@RegisterForReflection
public class CompanyDTO implements Serializable {
    
    public Long id;

    public String name;

    public String addressLine;

    public String city;

    public String country;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyDTO)) {
            return false;
        }

        return id != null && id.equals(((CompanyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", addressLine='" + addressLine + "'" +
            ", city='" + city + "'" +
            ", country='" + country + "'" +
            "}";
    }
}
