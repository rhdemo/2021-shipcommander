package com.redhat.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import com.redhat.service.SensorReadingsService;
import com.redhat.web.rest.errors.BadRequestAlertException;
import com.redhat.web.util.HeaderUtil;
import com.redhat.web.util.ResponseUtil;
import com.redhat.service.dto.SensorReadingsDTO;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.redhat.service.Paged;
import com.redhat.web.rest.vm.PageRequestVM;
import com.redhat.web.rest.vm.SortRequestVM;
import com.redhat.web.util.PaginationUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.redhat.domain.SensorReadings}.
 */
@Path("/api/sensor-readings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SensorReadingsResource {

    private final Logger log = LoggerFactory.getLogger(SensorReadingsResource.class);

    private static final String ENTITY_NAME = "sensorReadings";

    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    SensorReadingsService sensorReadingsService;
    /**
     * {@code POST  /sensor-readings} : Create a new sensorReadings.
     *
     * @param sensorReadingsDTO the sensorReadingsDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new sensorReadingsDTO, or with status {@code 400 (Bad Request)} if the sensorReadings has already an ID.
     */
    @POST
    public Response createSensorReadings(SensorReadingsDTO sensorReadingsDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save SensorReadings : {}", sensorReadingsDTO);
        if (sensorReadingsDTO.id != null) {
            throw new BadRequestAlertException("A new sensorReadings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = sensorReadingsService.persistOrUpdate(sensorReadingsDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /sensor-readings} : Updates an existing sensorReadings.
     *
     * @param sensorReadingsDTO the sensorReadingsDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated sensorReadingsDTO,
     * or with status {@code 400 (Bad Request)} if the sensorReadingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sensorReadingsDTO couldn't be updated.
     */
    @PUT
    public Response updateSensorReadings(SensorReadingsDTO sensorReadingsDTO) {
        log.debug("REST request to update SensorReadings : {}", sensorReadingsDTO);
        if (sensorReadingsDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = sensorReadingsService.persistOrUpdate(sensorReadingsDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sensorReadingsDTO.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /sensor-readings/:id} : delete the "id" sensorReadings.
     *
     * @param id the id of the sensorReadingsDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteSensorReadings(@PathParam("id") Long id) {
        log.debug("REST request to delete SensorReadings : {}", id);
        sensorReadingsService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /sensor-readings} : get all the sensorReadings.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of sensorReadings in body.
     */
    @GET
    public Response getAllSensorReadings(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo) {
        log.debug("REST request to get a page of SensorReadings");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<SensorReadingsDTO> result = sensorReadingsService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }


    /**
     * {@code GET  /sensor-readings/:id} : get the "id" sensorReadings.
     *
     * @param id the id of the sensorReadingsDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the sensorReadingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getSensorReadings(@PathParam("id") Long id) {
        log.debug("REST request to get SensorReadings : {}", id);
        Optional<SensorReadingsDTO> sensorReadingsDTO = sensorReadingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sensorReadingsDTO);
    }
}
