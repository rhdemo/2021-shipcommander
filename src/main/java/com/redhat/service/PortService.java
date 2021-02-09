package com.redhat.service;

import io.quarkus.panache.common.Page;
import com.redhat.domain.Port;
import com.redhat.service.dto.PortDTO;
import com.redhat.service.mapper.PortMapper;
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
public class PortService {

    private final Logger log = LoggerFactory.getLogger(PortService.class);

    @Inject
    PortMapper portMapper;

    @Transactional
    public PortDTO persistOrUpdate(PortDTO portDTO) {
        log.debug("Request to save Port : {}", portDTO);
        var port = portMapper.toEntity(portDTO);
        port = Port.persistOrUpdate(port);
        return portMapper.toDto(port);
    }

    /**
     * Delete the Port by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Port : {}", id);
        Port.findByIdOptional(id).ifPresent(port -> {
            port.delete();
        });
    }

    /**
     * Get one port by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PortDTO> findOne(Long id) {
        log.debug("Request to get Port : {}", id);
        return Port.findByIdOptional(id)
            .map(port -> portMapper.toDto((Port) port)); 
    }

    /**
     * Get all the ports.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<PortDTO> findAll(Page page) {
        log.debug("Request to get all Ports");
        return new Paged<>(Port.findAll().page(page))
            .map(port -> portMapper.toDto((Port) port));
    }



}
