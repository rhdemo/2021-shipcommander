package com.redhat.service.mapper;


import com.redhat.domain.*;
import com.redhat.service.dto.PortDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Port} and its DTO {@link PortDTO}.
 */
@Mapper(componentModel = "cdi", uses = {})
public interface PortMapper extends EntityMapper<PortDTO, Port> {



    default Port fromId(Long id) {
        if (id == null) {
            return null;
        }
        Port port = new Port();
        port.id = id;
        return port;
    }
}
