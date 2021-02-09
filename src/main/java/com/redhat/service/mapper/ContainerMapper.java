package com.redhat.service.mapper;


import com.redhat.domain.*;
import com.redhat.service.dto.ContainerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Container} and its DTO {@link ContainerDTO}.
 */
@Mapper(componentModel = "cdi", uses = {CompanyMapper.class, ShipmentMapper.class})
public interface ContainerMapper extends EntityMapper<ContainerDTO, Container> {

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.name", target = "senderName")
    @Mapping(source = "receiver.id", target = "receiverId")
    @Mapping(source = "receiver.name", target = "receiverName")
    @Mapping(source = "shipment.id", target = "shipmentId")
    ContainerDTO toDto(Container container);

    @Mapping(source = "senderId", target = "sender")
    @Mapping(source = "receiverId", target = "receiver")
    @Mapping(target = "readings", ignore = true)
    @Mapping(source = "shipmentId", target = "shipment")
    Container toEntity(ContainerDTO containerDTO);

    default Container fromId(Long id) {
        if (id == null) {
            return null;
        }
        Container container = new Container();
        container.id = id;
        return container;
    }
}
