package com.redhat.service;

import io.quarkus.panache.common.Page;
import com.redhat.domain.Shipment;
import com.redhat.service.dto.ShipmentDTO;
import com.redhat.service.mapper.ShipmentMapper;
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
public class ShipmentService {

    private final Logger log = LoggerFactory.getLogger(ShipmentService.class);

    @Inject
    ShipmentMapper shipmentMapper;

    @Transactional
    public ShipmentDTO persistOrUpdate(ShipmentDTO shipmentDTO) {
        log.debug("Request to save Shipment : {}", shipmentDTO);
        var shipment = shipmentMapper.toEntity(shipmentDTO);
        shipment = Shipment.persistOrUpdate(shipment);
        return shipmentMapper.toDto(shipment);
    }

    /**
     * Delete the Shipment by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Shipment : {}", id);
        Shipment.findByIdOptional(id).ifPresent(shipment -> {
            shipment.delete();
        });
    }

    /**
     * Get one shipment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ShipmentDTO> findOne(Long id) {
        log.debug("Request to get Shipment : {}", id);
        return Shipment.findByIdOptional(id)
            .map(shipment -> shipmentMapper.toDto((Shipment) shipment)); 
    }

    /**
     * Get all the shipments.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<ShipmentDTO> findAll(Page page) {
        log.debug("Request to get all Shipments");
        return new Paged<>(Shipment.findAll().page(page))
            .map(shipment -> shipmentMapper.toDto((Shipment) shipment));
    }



}
