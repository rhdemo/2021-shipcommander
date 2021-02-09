package com.redhat.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import com.redhat.service.ShipmentService;
import com.redhat.web.rest.errors.BadRequestAlertException;
import com.redhat.web.util.HeaderUtil;
import com.redhat.web.util.ResponseUtil;
import com.redhat.service.dto.ShipmentDTO;

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
 * REST controller for managing {@link com.redhat.domain.Shipment}.
 */
@Path("/api/shipments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ShipmentResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentResource.class);

    private static final String ENTITY_NAME = "shipment";

    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    ShipmentService shipmentService;
    /**
     * {@code POST  /shipments} : Create a new shipment.
     *
     * @param shipmentDTO the shipmentDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new shipmentDTO, or with status {@code 400 (Bad Request)} if the shipment has already an ID.
     */
    @POST
    public Response createShipment(ShipmentDTO shipmentDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save Shipment : {}", shipmentDTO);
        if (shipmentDTO.id != null) {
            throw new BadRequestAlertException("A new shipment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = shipmentService.persistOrUpdate(shipmentDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /shipments} : Updates an existing shipment.
     *
     * @param shipmentDTO the shipmentDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated shipmentDTO,
     * or with status {@code 400 (Bad Request)} if the shipmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipmentDTO couldn't be updated.
     */
    @PUT
    public Response updateShipment(ShipmentDTO shipmentDTO) {
        log.debug("REST request to update Shipment : {}", shipmentDTO);
        if (shipmentDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = shipmentService.persistOrUpdate(shipmentDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shipmentDTO.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /shipments/:id} : delete the "id" shipment.
     *
     * @param id the id of the shipmentDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteShipment(@PathParam("id") Long id) {
        log.debug("REST request to delete Shipment : {}", id);
        shipmentService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /shipments} : get all the shipments.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of shipments in body.
     */
    @GET
    public Response getAllShipments(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo) {
        log.debug("REST request to get a page of Shipments");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<ShipmentDTO> result = shipmentService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }


    /**
     * {@code GET  /shipments/:id} : get the "id" shipment.
     *
     * @param id the id of the shipmentDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the shipmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getShipment(@PathParam("id") Long id) {
        log.debug("REST request to get Shipment : {}", id);
        Optional<ShipmentDTO> shipmentDTO = shipmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentDTO);
    }
}
