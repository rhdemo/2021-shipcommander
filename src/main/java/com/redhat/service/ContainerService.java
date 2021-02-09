package com.redhat.service;

import io.quarkus.panache.common.Page;
import com.redhat.domain.Container;
import com.redhat.service.dto.ContainerDTO;
import com.redhat.service.mapper.ContainerMapper;
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
public class ContainerService {

    private final Logger log = LoggerFactory.getLogger(ContainerService.class);

    @Inject
    ContainerMapper containerMapper;

    @Transactional
    public ContainerDTO persistOrUpdate(ContainerDTO containerDTO) {
        log.debug("Request to save Container : {}", containerDTO);
        var container = containerMapper.toEntity(containerDTO);
        container = Container.persistOrUpdate(container);
        return containerMapper.toDto(container);
    }

    /**
     * Delete the Container by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Container : {}", id);
        Container.findByIdOptional(id).ifPresent(container -> {
            container.delete();
        });
    }

    /**
     * Get one container by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ContainerDTO> findOne(Long id) {
        log.debug("Request to get Container : {}", id);
        return Container.findByIdOptional(id)
            .map(container -> containerMapper.toDto((Container) container)); 
    }

    /**
     * Get all the containers.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<ContainerDTO> findAll(Page page) {
        log.debug("Request to get all Containers");
        return new Paged<>(Container.findAll().page(page))
            .map(container -> containerMapper.toDto((Container) container));
    }



}
