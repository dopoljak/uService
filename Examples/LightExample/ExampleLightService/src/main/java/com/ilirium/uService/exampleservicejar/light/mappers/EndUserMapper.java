package com.ilirium.uService.exampleservicejar.light.mappers;

import com.example.demo.database.entities.EndUser;
import com.ilirium.uService.exampleservicejar.light.dtos.EndUserDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author DoDo
 */
public class EndUserMapper {

    public static Collection<EndUserDTO> map(Collection<EndUser> entities) {
        Collection<EndUserDTO> dtos = new ArrayList<>(entities.size());
        for (EndUser e : entities) {
            EndUserDTO dto = new EndUserDTO();
            dto.setId(e.getId());
            dto.setPassword(e.getPassword());
            dto.setUsername(e.getUsername());
            dtos.add(dto);
        }
        return dtos;
    }
}
