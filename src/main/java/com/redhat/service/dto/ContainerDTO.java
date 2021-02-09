package com.redhat.service.dto;


import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.redhat.domain.Container} entity.
 */
@RegisterForReflection
public class ContainerDTO implements Serializable {
    
    public Long id;

    public String description;

    public Long senderId;
    public String senderName;
    public Long receiverId;
    public String receiverName;
    public Long shipmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContainerDTO)) {
            return false;
        }

        return id != null && id.equals(((ContainerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContainerDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", senderId=" + senderId +
            ", senderName='" + senderName + "'" +
            ", receiverId=" + receiverId +
            ", receiverName='" + receiverName + "'" +
            ", shipmentId=" + shipmentId +
            "}";
    }
}
