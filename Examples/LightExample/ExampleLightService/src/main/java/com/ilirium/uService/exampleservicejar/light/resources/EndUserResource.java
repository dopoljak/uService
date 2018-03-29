package com.ilirium.uService.exampleservicejar.light.resources;

import com.example.demo.database.entities.EndUser;
import com.example.demo.database.repositories.EndUserRepository;
import com.ilirium.repository.sql2o.repository.commons.Pagination;
import com.ilirium.uService.exampleservicejar.light.dtos.EndUserDTO;
import com.ilirium.uService.exampleservicejar.light.mappers.EndUserMapper;
import com.ilirium.uservice.undertow.voidpack.base.AbstractResponseResource;
import io.undertow.server.HttpServerExchange;

import java.util.Collection;

/**
 * @author dpoljak
 */
public class EndUserResource extends AbstractResponseResource {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(EndUserResource.class);
    private static final EndUserRepository endUserRepository = new EndUserRepository();

    @Override
    public Object handle(HttpServerExchange exchange) {
        Pagination pagination = pagination(exchange.getQueryParameters());
        Collection<EndUser> entities = endUserRepository.getAll(pagination);
        Collection<EndUserDTO> dtos = EndUserMapper.map(entities);
        LOGGER.info("EndUserDTO resource, size = {}", dtos.size());
        return dtos;
    }

}
