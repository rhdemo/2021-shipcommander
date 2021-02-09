package com.redhat.service;

import io.quarkus.panache.common.Page;
import com.redhat.domain.SensorReadings;
import com.redhat.service.dto.SensorReadingsDTO;
import com.redhat.service.mapper.SensorReadingsMapper;
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
public class SensorReadingsService {

    private final Logger log = LoggerFactory.getLogger(SensorReadingsService.class);

    @Inject
    SensorReadingsMapper sensorReadingsMapper;

    @Transactional
    public SensorReadingsDTO persistOrUpdate(SensorReadingsDTO sensorReadingsDTO) {
        log.debug("Request to save SensorReadings : {}", sensorReadingsDTO);
        var sensorReadings = sensorReadingsMapper.toEntity(sensorReadingsDTO);
        sensorReadings = SensorReadings.persistOrUpdate(sensorReadings);
        return sensorReadingsMapper.toDto(sensorReadings);
    }

    /**
     * Delete the SensorReadings by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete SensorReadings : {}", id);
        SensorReadings.findByIdOptional(id).ifPresent(sensorReadings -> {
            sensorReadings.delete();
        });
    }

    /**
     * Get one sensorReadings by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SensorReadingsDTO> findOne(Long id) {
        log.debug("Request to get SensorReadings : {}", id);
        return SensorReadings.findByIdOptional(id)
            .map(sensorReadings -> sensorReadingsMapper.toDto((SensorReadings) sensorReadings)); 
    }

    /**
     * Get all the sensorReadings.
     * @param page the pagination information.
     * @return the list of entities.
     */
    public Paged<SensorReadingsDTO> findAll(Page page) {
        log.debug("Request to get all SensorReadings");
        return new Paged<>(SensorReadings.findAll().page(page))
            .map(sensorReadings -> sensorReadingsMapper.toDto((SensorReadings) sensorReadings));
    }



}
