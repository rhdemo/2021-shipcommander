package com.redhat.service.mapper;


import com.redhat.domain.*;
import com.redhat.service.dto.ShipmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shipment} and its DTO {@link ShipmentDTO}.
 */
@Mapper(componentModel = "cdi", uses = {PortMapper.class, ShipMapper.class})
public interface ShipmentMapper extends EntityMapper<ShipmentDTO, Shipment> {

    @Mapping(source = "startPort.id", target = "startPortId")
    @Mapping(source = "startPort.name", target = "startPortName")
    @Mapping(source = "endPort.id", target = "endPortId")
    @Mapping(source = "endPort.name", target = "endPortName")
    @Mapping(source = "ship.id", target = "shipId")
    @Mapping(source = "ship.name", target = "shipName")
    ShipmentDTO toDto(Shipment shipment);

    @Mapping(source = "startPortId", target = "startPort")
    @Mapping(source = "endPortId", target = "endPort")
    @Mapping(source = "shipId", target = "ship")
    @Mapping(target = "containers", ignore = true)
    Shipment toEntity(ShipmentDTO shipmentDTO);

    default Shipment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shipment shipment = new Shipment();
        shipment.id = id;
        return shipment;
    }
}
