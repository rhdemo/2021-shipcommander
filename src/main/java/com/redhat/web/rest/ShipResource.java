package com.redhat.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import com.redhat.service.ShipService;
import com.redhat.web.rest.errors.BadRequestAlertException;
import com.redhat.web.util.HeaderUtil;
import com.redhat.web.util.ResponseUtil;
import com.redhat.service.dto.ShipDTO;

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
 * REST controller for managing {@link com.redhat.domain.Ship}.
 */
@Path("/api/ships")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ShipResource {

    private final Logger log = LoggerFactory.getLogger(ShipResource.class);

    private static final String ENTITY_NAME = "ship";

    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    ShipService shipService;
    /**
     * {@code POST  /ships} : Create a new ship.
     *
     * @param shipDTO the shipDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new shipDTO, or with status {@code 400 (Bad Request)} if the ship has already an ID.
     */
    @POST
    public Response createShip(ShipDTO shipDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save Ship : {}", shipDTO);
        if (shipDTO.id != null) {
            throw new BadRequestAlertException("A new ship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = shipService.persistOrUpdate(shipDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /ships} : Updates an existing ship.
     *
     * @param shipDTO the shipDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated shipDTO,
     * or with status {@code 400 (Bad Request)} if the shipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipDTO couldn't be updated.
     */
    @PUT
    public Response updateShip(ShipDTO shipDTO) {
        log.debug("REST request to update Ship : {}", shipDTO);
        if (shipDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = shipService.persistOrUpdate(shipDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shipDTO.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /ships/:id} : delete the "id" ship.
     *
     * @param id the id of the shipDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteShip(@PathParam("id") Long id) {
        log.debug("REST request to delete Ship : {}", id);
        shipService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /ships} : get all the ships.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of ships in body.
     */
    @GET
    public Response getAllShips(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo) {
        log.debug("REST request to get a page of Ships");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<ShipDTO> result = shipService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }


    /**
     * {@code GET  /ships/:id} : get the "id" ship.
     *
     * @param id the id of the shipDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the shipDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getShip(@PathParam("id") Long id) {
        log.debug("REST request to get Ship : {}", id);
        Optional<ShipDTO> shipDTO = shipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipDTO);
    }
}
