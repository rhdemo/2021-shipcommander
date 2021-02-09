package com.redhat.web.rest;

import static javax.ws.rs.core.UriBuilder.fromPath;

import com.redhat.service.ContainerService;
import com.redhat.web.rest.errors.BadRequestAlertException;
import com.redhat.web.util.HeaderUtil;
import com.redhat.web.util.ResponseUtil;
import com.redhat.service.dto.ContainerDTO;

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
 * REST controller for managing {@link com.redhat.domain.Container}.
 */
@Path("/api/containers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ContainerResource {

    private final Logger log = LoggerFactory.getLogger(ContainerResource.class);

    private static final String ENTITY_NAME = "container";

    @ConfigProperty(name = "application.name")
    String applicationName;


    @Inject
    ContainerService containerService;
    /**
     * {@code POST  /containers} : Create a new container.
     *
     * @param containerDTO the containerDTO to create.
     * @return the {@link Response} with status {@code 201 (Created)} and with body the new containerDTO, or with status {@code 400 (Bad Request)} if the container has already an ID.
     */
    @POST
    public Response createContainer(ContainerDTO containerDTO, @Context UriInfo uriInfo) {
        log.debug("REST request to save Container : {}", containerDTO);
        if (containerDTO.id != null) {
            throw new BadRequestAlertException("A new container cannot already have an ID", ENTITY_NAME, "idexists");
        }
        var result = containerService.persistOrUpdate(containerDTO);
        var response = Response.created(fromPath(uriInfo.getPath()).path(result.id.toString()).build()).entity(result);
        HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code PUT  /containers} : Updates an existing container.
     *
     * @param containerDTO the containerDTO to update.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the updated containerDTO,
     * or with status {@code 400 (Bad Request)} if the containerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the containerDTO couldn't be updated.
     */
    @PUT
    public Response updateContainer(ContainerDTO containerDTO) {
        log.debug("REST request to update Container : {}", containerDTO);
        if (containerDTO.id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        var result = containerService.persistOrUpdate(containerDTO);
        var response = Response.ok().entity(result);
        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, containerDTO.id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code DELETE  /containers/:id} : delete the "id" container.
     *
     * @param id the id of the containerDTO to delete.
     * @return the {@link Response} with status {@code 204 (NO_CONTENT)}.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteContainer(@PathParam("id") Long id) {
        log.debug("REST request to delete Container : {}", id);
        containerService.delete(id);
        var response = Response.noContent();
        HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()).forEach(response::header);
        return response.build();
    }

    /**
     * {@code GET  /containers} : get all the containers.
     *
     * @param pageRequest the pagination information.
     * @return the {@link Response} with status {@code 200 (OK)} and the list of containers in body.
     */
    @GET
    public Response getAllContainers(@BeanParam PageRequestVM pageRequest, @BeanParam SortRequestVM sortRequest, @Context UriInfo uriInfo) {
        log.debug("REST request to get a page of Containers");
        var page = pageRequest.toPage();
        var sort = sortRequest.toSort();
        Paged<ContainerDTO> result = containerService.findAll(page);
        var response = Response.ok().entity(result.content);
        response = PaginationUtil.withPaginationInfo(response, uriInfo, result);
        return response.build();
    }


    /**
     * {@code GET  /containers/:id} : get the "id" container.
     *
     * @param id the id of the containerDTO to retrieve.
     * @return the {@link Response} with status {@code 200 (OK)} and with body the containerDTO, or with status {@code 404 (Not Found)}.
     */
    @GET
    @Path("/{id}")

    public Response getContainer(@PathParam("id") Long id) {
        log.debug("REST request to get Container : {}", id);
        Optional<ContainerDTO> containerDTO = containerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(containerDTO);
    }
}
