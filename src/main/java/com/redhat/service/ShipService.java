package com.redhat.service;

import io.quarkus.panache.common.Page;
import com.redhat.domain.Ship;
import com.redhat.service.dto.ShipDTO;
import com.redhat.service.mapper.ShipMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class ShipService {

    private final Logger log = LoggerFactory.getLogger(ShipService.class);

    @Inject
    ShipMapper shipMapper;

    @Transactional
    public ShipDTO persistOrUpdate(ShipDTO shipDTO) {
        log.debug("Request to save Ship : {}", shipDTO);
        var ship = shipMapper.toEntity(shipDTO);
        ship = Ship.persistOrUpdate(ship);
        return shipMapper.toDto(ship);
    }

    /**
     * Delete the Ship by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Ship : {}", id);
        Ship.findByIdOptional(id).ifPresent(ship -> {
            ship.delete();
        });
    }

    /**
     * Get one ship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ShipDTO> findOne(Long id) {
        log.debug("Request to get Ship : {}", id);
        return Ship.findByIdOptional(id)
            .map(ship -> shipMapper.toDto((Ship) ship)); 
    }

    /**
     * Get all the ships.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<ShipDTO> findAll(Page page) {
        log.debug("Request to get all Ships");
        return new Paged<>(Ship.findAll().page(page))
            .map(ship -> shipMapper.toDto((Ship) ship));
    }



}
