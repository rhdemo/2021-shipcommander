package com.redhat.service.mapper;


import com.redhat.domain.*;
import com.redhat.service.dto.ShipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ship} and its DTO {@link ShipDTO}.
 */
@Mapper(componentModel = "cdi", uses = {})
public interface ShipMapper extends EntityMapper<ShipDTO, Ship> {



    default Ship fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ship ship = new Ship();
        ship.id = id;
        return ship;
    }
}
