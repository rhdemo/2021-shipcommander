package com.redhat.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import com.redhat.service.PortService;
import com.redhat.web.rest.errors.BadRequestAlertException;
import com.redhat.web.util.HeaderUtil;
import com.redhat.web.util.ResponseUtil;
import com.redhat.service.dto.PortDTO;

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
 * REST controller for managing {@link com.redhat.domain.Port}.
 */
@Path("/api/ports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PortResource {

    private final Logger log = LoggerFactory.getLogger(PortResource.class);

    private static final String ENTITY_NAME = "port";

    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    PortService portService;
    /**
     * {@code POST  /ports} : Create a new port.
     *
     * @param portDTO the portDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new portDTO, or with status {@code 400 (Bad Request)} if the port has already an ID.
     */
    @POST
    public Response createPort(PortDTO portDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save Port : {}", portDTO);
        if (portDTO.id != null) {
            throw new BadRequestAlertException("A new port cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = portService.persistOrUpdate(portDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /ports} : Updates an existing port.
     *
     * @param portDTO the portDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated portDTO,
     * or with status {@code 400 (Bad Request)} if the portDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the portDTO couldn't be updated.
     */
    @PUT
    public Response updatePort(PortDTO portDTO) {
        log.debug("REST request to update Port : {}", portDTO);
        if (portDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = portService.persistOrUpdate(portDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, portDTO.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /ports/:id} : delete the "id" port.
     *
     * @param id the id of the portDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deletePort(@PathParam("id") Long id) {
        log.debug("REST request to delete Port : {}", id);
        portService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /ports} : get all the ports.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of ports in body.
     */
    @GET
    public Response getAllPorts(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo) {
        log.debug("REST request to get a page of Ports");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<PortDTO> result = portService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }


    /**
     * {@code GET  /ports/:id} : get the "id" port.
     *
     * @param id the id of the portDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the portDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getPort(@PathParam("id") Long id) {
        log.debug("REST request to get Port : {}", id);
        Optional<PortDTO> portDTO = portService.findOne(id);
        return ResponseUtil.wrapOrNotFound(portDTO);
    }
}
