package com.ilirium.uService.exampleservicejar.light.resources;

import com.example.demo.database.entities.EndUser;
import com.example.demo.database.repositories.EndUserRepository;
import com.ilirium.repository.sql2o.repository.commons.Pagination;
import com.ilirium.uService.exampleservicejar.light.dtos.EndUserDTO;
import com.ilirium.uService.exampleservicejar.light.mappers.EndUserMapper;
import com.ilirium.uservice.undertow.base.AbstractResponseResource;
import io.undertow.server.HttpServerExchange;

import java.util.Collection;

/**
 * @author dpoljak
 */
public class EndUserResource extends AbstractResponseResource {

    private final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EndUserResource.class);
    private final EndUserRepository endUserRepository = new EndUserRepository();
    private final EndUserMapper endUserMapper = new EndUserMapper();

    @Override
    public Object handle(HttpServerExchange exchange) {
        Pagination pagination = pagination(exchange.getQueryParameters());
        Collection<EndUser> entities = endUserRepository.getAll(pagination);
        Collection<EndUserDTO> dtos = endUserMapper.map(entities);
        LOGGER.info("EndUserDTO resource, size = {}", dtos.size());
        return dtos;
    }

}
