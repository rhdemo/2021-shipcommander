package com.redhat.service.mapper;


import com.redhat.domain.*;
import com.redhat.service.dto.SensorReadingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SensorReadings} and its DTO {@link SensorReadingsDTO}.
 */
@Mapper(componentModel = "cdi", uses = {ContainerMapper.class})
public interface SensorReadingsMapper extends EntityMapper<SensorReadingsDTO, SensorReadings> {

    @Mapping(source = "container.id", target = "containerId")
    SensorReadingsDTO toDto(SensorReadings sensorReadings);

    @Mapping(source = "containerId", target = "container")
    SensorReadings toEntity(SensorReadingsDTO sensorReadingsDTO);

    default SensorReadings fromId(Long id) {
        if (id == null) {
            return null;
        }
        SensorReadings sensorReadings = new SensorReadings();
        sensorReadings.id = id;
        return sensorReadings;
    }
}
