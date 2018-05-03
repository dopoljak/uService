package com.ilirium.uService.exampleservicejar.resource;

import com.example.demo.client.dto.*;
import com.example.demo.database.entities.*;
import com.example.demo.database.repositories.*;
import com.ilirium.database.request.*;
import com.ilirium.database.templates.*;
import com.ilirium.uService.exampleservicejar.mappers.*;
import io.swagger.annotations.*;

import javax.enterprise.context.*;
import javax.enterprise.event.*;
import javax.inject.*;
import javax.transaction.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;


/**
 * @author dpoljak
 */
@Api(value = "/", description = "EndUser resource", tags = "EndUser")
@Path("/endusers")
@RequestScoped
@Transactional
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EndUserResource {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EndUserResource.class);

    @Inject
    private Event<AbstractEntity> domainObjectEvents;

    @Inject
    private EndUserMapper endUserMapper;

    @Inject
    private EndUserRepository endUserRepository;

    @ApiOperation(
            value = "Get list of EndUsers2",
            notes = "Get list of EndUsers2 ...",
            response = WrapperDTO.class
    )
    @GET
    @Path("wrapped_and_paged")
    public WrapperDTO<Collection<EndUserDTO>> getEndUser(
            @QueryParam(value = "offset") //@DefaultValue("0")
                    Integer offset,
            @QueryParam(value = "limit") //@DefaultValue("50")
                    Integer limit,
            @QueryParam(value = "page") Integer page) {

        LOGGER.info(">>  get(offset = {}, limit = {}, page = {})", offset, limit, page);

        PageRequest pageRequest = PageRequest.defaultIfWrongConf(offset, limit, page);
        LOGGER.info("CalculatedOffset = {}, Limit = {}", pageRequest.getCalculatedOffset(), pageRequest.getLimit());

        Collection<EndUser> endUsers = endUserRepository.findPagedOrdered(pageRequest, null);
        Collection<EndUserDTO> endUserDTOs = endUserMapper.map(endUsers);
        Long count = endUserRepository.countAll();

        LOGGER.info("<<  get");

        return new WrapperDTO<>(count, endUserDTOs);
    }

    @ApiOperation(
            value = "Get list of EndUsers",
            notes = "Get list of EndUsers ...",
            response = EndUserDTO.class,
            responseContainer = "Collection"
    )
    @GET
    @Path("all")
    public Collection<EndUserDTO> getEndUsers() {
        Collection<EndUser> endUsers = endUserRepository.getAll();
        return endUserMapper.map(endUsers);
    }

    @ApiOperation(
            value = "Create EndUsers",
            notes = "Create EndUsers ...",
            response = EndUserDTO.class
    )
    @POST
    public EndUserDTO createEndUser(EndUserDTO endUserDTO) {

//        if (endUserDTO.getId() != null) {
//            throw new AppException(CommonException.ENDUSER_ID_MUST_BE_NULL);
//        }

        EndUser endUser = endUserMapper.map(endUserDTO);
        endUserRepository.persist(endUser);
        domainObjectEvents.fire(endUser);
        endUserDTO.setId(endUser.getId());
        return endUserDTO;
    }
}
